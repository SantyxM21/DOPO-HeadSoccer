package presentation;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import domain.DopoSoccer;
import domain.DopoSoccerException;
import domain.Match;
import domain.Player;

public class GamePanel extends JPanel{
    private final Dimension GAME_SIZE;

    private Image image;
    private Graphics graphics;

    private Image leftPlayerImage;
    private Image rightPlayerImage;
    private DopoSoccer game;
    private KeyHandler keyH = new KeyHandler();
    private Timer gameLoop;
    private long lastFrame = 0;                          
    private static final double MAX_FRAME_SECONDS = 0.1; 
    private MainPanel main;
    private String leftCountry;
    private String rightCountry;
    private JDialog winnerDialog;
    private final CustomButton menuButton = new CustomButton("Volver al menu");
    private JDialog pauseDialog;
    private final CustomButton resumeButton = new CustomButton("Reanudar");
    private final CustomButton pauseMenuButton = new CustomButton("Menu");
    private final CustomButton saveButton = new CustomButton("Guardar");
    private static final Image POPUP_BACKGROUND = DopoSoccerGUI.loadImage("/resources/fondo_popup.png");
    private static final Image AURA_IMAGE = DopoSoccerGUI.loadImage("/resources/aura.png");
    private static final double AURA_SCALE = 1.6;
    private static final Color POPUP_TEXT = new Color(27, 42, 85);

    private int gameTimeSecs = 0;

    private final JProgressBar leftPower = new JProgressBar(0, 100);
    private final JProgressBar rightPower = new JProgressBar(0, 100);
    private final JLabel leftName = new JLabel("Player 1", SwingConstants.RIGHT);
    private final JLabel rightName = new JLabel("Machine", SwingConstants.LEFT);
    private final JLabel leftFlag = new JLabel();
    private final JLabel rightFlag = new JLabel();
    private final JLabel leftGoals = new JLabel("2", SwingConstants.CENTER);
    private final JLabel rightGoals = new JLabel("1", SwingConstants.CENTER);
    private final JLabel timeLabel = new JLabel("0:00", SwingConstants.CENTER);
    private final JLabel timeTitle = new JLabel("Tiempo", SwingConstants.CENTER);
    private final RoundButton pauseButton = new RoundButton();

    // Colores del marcador
    private static final Color POWER_FILL = new Color(24, 40, 82);

    public GamePanel(DopoSoccer game){
        super();
        this.game = game;
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        setFocusable(true);                              
    }

    public JPanel prepareGamePage(){
        setLayout(null);                                    // el juego se dibuja con Graphics, el marcador va en una posicion fija arriba

        JPanel scoreboard = createScoreboard();
        scoreboard.setBounds(0, 0, GAME_SIZE.width, GAME_SIZE.height / 7);  
        add(scoreboard);

        return this;
    }

    public void prepareController(MainPanel panel){
        this.main = panel;
        addKeyListener(keyH);

        // Game loop: Match.FPS veces por segundo se llama a update()
        stopGameLoop();                                     // nunca debe haber dos loops actualizando el mismo juego
        gameLoop = new Timer(1000 / Match.FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                update();
            }
        });
        gameLoop.start();

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                winnerDialog.dispose();
                game.endGame();
                main.showPage("Home menu");
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!game.hasMatch() || game.isFinished()) return;
                gameLoop.stop();                                    // congela el juego 
                showPausePopup();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                pauseDialog.dispose();
                lastFrame = 0;                                    
                gameLoop.start();
                requestFocusInWindow();                             // para que el teclado vuelva a mover a los jugadores
            }
        });

        pauseMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                pauseDialog.dispose();
                game.endGame();
                gameLoop.start();                                   // el loop queda listo para el siguiente partido
                main.showPage("Home menu");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                MainPanel.funcUnderConstruction("Guardar partida: la funcionalidad se encuentra en construccion");
            }
        });
    }

    private double elapsedSeconds(){
        long now = System.nanoTime();
        double seconds = (lastFrame == 0) ? Match.FRAME_SECONDS : (now - lastFrame) / 1e9;
        lastFrame = now;
        return Math.min(seconds, MAX_FRAME_SECONDS);
    }

    /* Detiene el game loop toca llamar antes de descartar este panel */
    public void stopGameLoop(){
        if (gameLoop != null) gameLoop.stop();
    }

    // Mueve al jugador segun las teclas que esten presionadas 
    private void update(){
        if(!game.hasMatch() || game.isFinished()) return;    

        if(keyH.powerPressed) game.activatePower(Match.LEFT_PLAYER);
        if(keyH.kickPressed) game.kick(Match.LEFT_PLAYER);                 
        int direction = 0;
        // Se usa -= y += en caso de qque ambas teclas se presionen al tiempo se "cancelen"
        if(keyH.leftPressed) direction -= 1;
        if(keyH.rightPressed) direction += 1;
        if(keyH.jumpPressed) game.jump(Match.LEFT_PLAYER);
        if(direction != 0) game.move(Match.LEFT_PLAYER, direction);

        if(keyH.powerPressedR) game.activatePower(Match.RIGHT_PLAYER);
        if(keyH.kickPressedR) game.kick(Match.RIGHT_PLAYER);   
        int direction2 = 0;
        if(keyH.leftPressedR) direction2 -= 1;
        if(keyH.rightPressedR) direction2 += 1;
        if(keyH.jumpPressedR) game.jump(Match.RIGHT_PLAYER);
        if(direction2 != 0) game.move(Match.RIGHT_PLAYER, direction2);

        game.update(elapsedSeconds());
        updateScoreboard();
        repaint();

        if (game.isFinished()) showWinnerPopup();          
    }

    /* Popup que anuncia el ganador, el marcador final y los puntos ganados por cada jugador */
    private void showWinnerPopup(){
        int winner = game.getWinner();
        int loser = (winner == Match.LEFT_PLAYER) ? Match.RIGHT_PLAYER : Match.LEFT_PLAYER;
        String winnerCountry = (winner == Match.LEFT_PLAYER) ? leftCountry : rightCountry;

        JDialog dialog = createPopup("Fin del partido", 1.6, 1.4);
        winnerDialog = dialog;

        int h = GAME_SIZE.height;
        JLabel title = popupLabel("¡GANADOR!", Font.BOLD, h / 10);
        title.setBorder(BorderFactory.createEmptyBorder(h / 20, 0, 0, 0));

        JLabel flag = new JLabel(DopoSoccerGUI.flagIcon(winnerCountry, h / 8), SwingConstants.CENTER);
        JLabel name = popupLabel(game.getPlayerName(winner) + " - " + winnerCountry, Font.BOLD, h / 22);
        JLabel score = popupLabel(game.getPlayerGoals(Match.LEFT_PLAYER) + "  -  " + game.getPlayerGoals(Match.RIGHT_PLAYER), Font.BOLD, h / 14);
        JLabel winnerPoints = popupLabel(pointsText(winner), Font.PLAIN, h / 28);
        JLabel loserPoints = popupLabel(pointsText(loser), Font.PLAIN, h / 28);

        JPanel info = new JPanel(new GridLayout(5, 1, 0, h / 80));
        info.setOpaque(false);
        for (JComponent c : new JComponent[]{flag, name, score, winnerPoints, loserPoints}) info.add(c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, h / 30));
        buttons.setOpaque(false);
        buttons.add(menuButton);

        dialog.add(title, BorderLayout.NORTH);
        dialog.add(info, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /* Menu de pausa: Reanudar, Menu y Guardar */
    private void showPausePopup(){
        JDialog dialog = createPopup("Pausa", 2.0, 2.2);
        pauseDialog = dialog;

        int h = GAME_SIZE.height;
        JLabel title = popupLabel("PAUSA", Font.BOLD, h / 10);
        title.setBorder(BorderFactory.createEmptyBorder(h / 20, 0, 0, 0));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 60, h / 25));
        buttons.setOpaque(false);
        buttons.add(resumeButton);
        buttons.add(pauseMenuButton);
        buttons.add(saveButton);

        dialog.add(title, BorderLayout.NORTH);
        dialog.add(buttons, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /* Dialogo modal con el fondo de los popups; solo se cierra con sus botones */
    private JDialog createPopup(String windowTitle, double widthDivisor, double heightDivisor){
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), windowTitle, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize((int)(GAME_SIZE.width / widthDivisor), (int)(GAME_SIZE.height / heightDivisor));
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setContentPane(new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (POPUP_BACKGROUND != null) g.drawImage(POPUP_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
            }
        });
        return dialog;
    }

    /* "Profile #1: +100 puntos (total 1250)" */
    private String pointsText(int side){
        return game.getPlayerName(side) + ": +" + game.getPointsEarned(side) + " puntos (total " + game.getPlayerPoints(side) + ")";
    }

    private JLabel popupLabel(String text, int style, int size){
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(POPUP_TEXT);
        return label;
    }

    @Override
    // Este metodo es llamado por setVisible(true) o tambien por repaint(), y muchos mas.
    public void paint(Graphics g){                  
        image = createImage(getWidth(),getHeight());     // Crea un lienzo vacio de height*width
        graphics = image.getGraphics();                  // Obtiene acceso para pintar en el lienzo (pincel)
        draw(graphics);                                  // Le da el pincel a todos los que quiera dibujar en el lienzo 
        g.drawImage(image, 0, 0, this);             // Copia la imagen ya terminada a la pantalla, desde la esquina superior izquierda (0, 0)
        paintChildren(g);                                // Dibuja los componentes (botón de pausa) encima del juego
    }

    public void draw(Graphics g){
        drawBackground(g);
        if(!game.hasMatch()) return;   
        drawField(g);                             // Primero el fondo para que quede detras de todo
        drawPlayer(g, Match.LEFT_PLAYER);
        drawPlayer(g, Match.RIGHT_PLAYER);
        drawBall(g);
    }

    private JPanel createScoreboard(){
        int barH = GAME_SIZE.height / 7;
        Font textFont = new Font("Arial", Font.BOLD, barH / 5);
        Font bigFont = new Font("Arial", Font.BOLD, barH / 4);

        // Barras de poder
        Dimension powerSize = new Dimension(GAME_SIZE.width / 10, barH / 4);
        for (JProgressBar power : new JProgressBar[]{leftPower, rightPower}) {
            power.setPreferredSize(powerSize);
            power.setForeground(POWER_FILL);                         // color de la parte llena
            power.setBackground(Color.WHITE);
            power.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        }
        leftPower.setValue(0);                                       // TODO: poder del jugador 1
        rightPower.setValue(60);                                     // TODO: poder del jugador 2

        // Textos
        leftName.setFont(textFont);
        rightName.setFont(textFont);
        leftGoals.setFont(bigFont);
        rightGoals.setFont(bigFont);
        timeLabel.setFont(bigFont);
        timeTitle.setFont(textFont);
        for (JLabel text : new JLabel[]{leftName, rightName, leftGoals, rightGoals, timeLabel, timeTitle}) {
            text.setForeground(Color.WHITE);                        
        }

        // Banderas
        int flagH = flagHeight();
        Dimension flagSize = new Dimension(flagH * 7 / 5, flagH);
        for (JLabel flag : new JLabel[]{leftFlag, rightFlag}) {
            flag.setPreferredSize(flagSize);
            flag.setHorizontalAlignment(SwingConstants.CENTER);
            flag.setOpaque(false);
        }

        // Botón de pausa
        int side = barH / 2;
        pauseButton.setText("II");
        pauseButton.setFont(new Font("Arial", Font.BOLD, side / 3));
        pauseButton.setPreferredSize(new Dimension(side, side));
        pauseButton.setFocusable(false);                     // si toma el foco, el teclado deja de mover a los jugadores

        int gap = GAME_SIZE.width / 60;

        // Centro: bandera 1 / tiempo / bandera 2
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, 0));
        center.setOpaque(false);
        center.add(stack(leftFlag, leftGoals));
        center.add(stack(timeTitle, timeLabel));
        center.add(stack(rightFlag, rightGoals));

        // Tres columnas iguales: jugador 1 / centro / jugador 2 
        JPanel row = new JPanel(new GridLayout(1, 3, gap, 0));
        row.setOpaque(false);
        row.add(stack(keepHeight(leftPower), leftName));
        row.add(center);
        row.add(stack(keepHeight(rightPower), rightName));

        // La pausa va aparte a la derecha
        JPanel pauseHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, barH / 6));
        pauseHolder.setOpaque(false);
        pauseHolder.add(pauseButton);

        JPanel scoreboard = new JPanel(new BorderLayout());
        scoreboard.setOpaque(false);                                 // transparente para que se vea el fondo del juego
        scoreboard.setBorder(BorderFactory.createEmptyBorder(barH / 10, gap * 2, barH / 10, 0));
        scoreboard.add(row, BorderLayout.CENTER);
        scoreboard.add(pauseHolder, BorderLayout.EAST);
        return scoreboard;
    }

    /*
    This method keeps a component's height while letting it stretch to the full width
    @param component the component to wrap
    @return a transparent panel with the component at the top
    */
    private JPanel keepHeight(JComponent component){
        JPanel holder = new JPanel(new BorderLayout());
        holder.setOpaque(false);
        holder.add(component, BorderLayout.NORTH);                   // NORTH respeta el alto y estira el ancho
        return holder;
    }

    /* Apila los componentes uno encima del otro */
    private JPanel stack(JComponent top, JLabel bottom){
        JPanel panel = new JPanel(new BorderLayout(0, 2));
        panel.setOpaque(false);
        panel.add(top, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }
        
    private void updateScoreboard(){
        boolean timeMode = game.getMatchType() == DopoSoccer.TIME_MODE;
        int titleSize = GAME_SIZE.height / 7 / (timeMode ? 5 : 7);      // en modo goles el titulo es mas largo osea letra mas pequeña para que quepa
        if (timeTitle.getFont().getSize() != titleSize) timeTitle.setFont(timeTitle.getFont().deriveFont((float) titleSize));

        if (timeMode) {
            timeTitle.setText("Tiempo");
            timeLabel.setText(game.textUnderScore());          // cuenta regresiva o MUERTE SUBITA
        } else {
            timeTitle.setText(game.textUnderScore());          // "Goles para ganar N"
            int time = game.getTime();                         // tiempo transcurrido
            timeLabel.setText(String.format("%d:%02d", time / 60, time % 60));
        }
        leftGoals.setText(String.valueOf(game.getPlayerGoals(Match.LEFT_PLAYER)));
        rightGoals.setText(String.valueOf(game.getPlayerGoals(Match.RIGHT_PLAYER)));
        leftPower.setValue(close((int)game.getPowerBar(Match.LEFT_PLAYER)));
        rightPower.setValue(close((int)game.getPowerBar(Match.RIGHT_PLAYER)));
    }

    private static int close(int n) {
        return (n / 10) * 10;
    }

    public void setCountries(String leftCountry, String rightCountry){
        this.leftCountry = leftCountry;
        this.rightCountry = rightCountry;
        this.lastFrame = 0;                                         // partido nuevo: el reloj empieza a medir desde cero
        leftFlag.setIcon(DopoSoccerGUI.flagIcon(leftCountry, flagHeight()));       
        rightFlag.setIcon(DopoSoccerGUI.flagIcon(rightCountry, flagHeight()));
        leftName.setText(game.getPlayerName(Match.LEFT_PLAYER));
        rightName.setText(game.getPlayerName(Match.RIGHT_PLAYER));

        leftPlayerImage = DopoSoccerGUI.loadImage("/resources/Jugador " + leftCountry + ".png");
        rightPlayerImage = DopoSoccerGUI.loadImage("/resources/Jugador " + rightCountry + ".png");
    }

    /* Alto del recuadro de las banderas del marcador */
    private int flagHeight(){
        return GAME_SIZE.height / 7 * 11 / 20;
    }

    public void drawBackground(Graphics g){
        if(DopoSoccerGUI.GAME_BACKGROUND != null) g.drawImage(DopoSoccerGUI.GAME_BACKGROUND, 0, 0, getWidth(), getHeight(), this);   // Escalada al tamaño del panel
    }

    public void drawPlayer(Graphics g, int player){
        int size = game.getPlayerSize(player);
        int x = game.getPlayerX(player);
        int y = game.getPlayerY(player);
        Image playerImage = (player == Match.LEFT_PLAYER) ? leftPlayerImage : rightPlayerImage;

        if (game.hasAura(player)) drawAura(g, x, y, size);
        if (playerImage != null && player == Match.LEFT_PLAYER) {
            // Volteada la imagen horizontalmente
            int imgW = playerImage.getWidth(this);
            int imgH = playerImage.getHeight(this);
            g.drawImage(playerImage, x + size, y, x, y + size, 0, 0, imgW, imgH, this);
        } else if (playerImage != null) {
            g.drawImage(playerImage, x, y, size, size, this);       // escalada al tamaño del jugador
        } else {
            g.setColor(Color.WHITE);                                 // si no hay imagen se dibuja un circulo
            g.fillOval(x, y, size, size);
        }
    }

    private void drawAura(Graphics g, int x, int y, int size){
        if (AURA_IMAGE == null) return;
        int auraSize = (int) (size * AURA_SCALE);
        g.drawImage(AURA_IMAGE, x + (size - auraSize) / 2, y + size - auraSize, auraSize, auraSize, this);   // centrada y apoyada en los pies
    }

    public void drawField(Graphics g){
        g.setColor(Color.WHITE);
        for(int side = Match.LEFT_PLAYER; side <= Match.RIGHT_PLAYER; side++){
            g.drawRect(game.getGoalFrameX(side), game.getGoalFrameY(), game.getGoalFrameWidth(), game.getGoalFrameHeight());
        }
    }

    public void drawBall(Graphics g){
        int diameter = game.getBallDiameter();

        g.setColor(Color.ORANGE);
        g.fillOval(game.getBallX(), game.getBallY(), diameter, diameter);
        g.setColor(Color.BLACK);
        g.drawOval(game.getBallX(), game.getBallY(), diameter, diameter);
    }
}

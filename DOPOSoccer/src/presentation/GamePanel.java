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
    private DopoSoccer game;
    private KeyHandler keyH = new KeyHandler();

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
        addKeyListener(keyH);     

        // Game loop 60 veces por segundo se llama a update()
        Timer gameLoop = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                update();
            }
        });
        gameLoop.start();
    }

    // Mueve al jugador segun las teclas que esten presionadas 
    private void update(){
        if(!game.hasMatch() || game.isFinished()) return;    

        if(keyH.kickPressed) game.kick(Match.LEFT_SIDE);                 
        int direction = 0;
        // Se usa -= y += en caso de qque ambas teclas se presionen al tiempo se "cancelen"
        if(keyH.leftPressed) direction -= 1;
        if(keyH.rightPressed) direction += 1;
        if(keyH.jumpPressed) game.jump(Match.LEFT_SIDE);
        if(direction != 0) game.move(Match.LEFT_SIDE, direction);

        if(keyH.kickPressedR) game.kick(Match.RIGHT_SIDE);   
        int direction2 = 0;
        if(keyH.leftPressedR) direction2 -= 1;
        if(keyH.rightPressedR) direction2 += 1;
        if(keyH.jumpPressedR) game.jump(Match.RIGHT_SIDE);
        if(direction2 != 0) game.move(Match.RIGHT_SIDE, direction2);

        game.update();
        updateTimer(game.getTime());
        repaint();
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
        drawPlayer(g, 1);
        drawPlayer(g, 2);
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
        JLabel timeTitle = new JLabel("Tiempo", SwingConstants.CENTER);
        timeTitle.setFont(textFont);
        for (JLabel text : new JLabel[]{leftName, rightName, leftGoals, rightGoals, timeLabel, timeTitle}) {
            text.setForeground(Color.WHITE);                        
        }

        // Banderas
        int flagH = barH * 11 / 20;
        Dimension flagSize = new Dimension(flagH * 7 / 5, flagH);
        for (JLabel flag : new JLabel[]{leftFlag, rightFlag}) {
            flag.setPreferredSize(flagSize);
            flag.setHorizontalAlignment(SwingConstants.CENTER);
            flag.setOpaque(false);
        }
        leftFlag.setIcon(DopoSoccerGUI.flagIcon(DopoSoccer.COUNTRIES[0], flagH));      // TODO: personaje del jugador 1
        rightFlag.setIcon(DopoSoccerGUI.flagIcon(DopoSoccer.COUNTRIES[2], flagH));     // TODO: personaje del jugador 2

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
        
    private void updateTimer(int time){
        int minutes = time / 60;     
        int seconds = time % 60;    
        timeLabel.setText(String.format("%d:%02d", minutes, seconds));   
    }

    public void drawBackground(Graphics g){
        if(DopoSoccerGUI.GAME_BACKGROUND != null) g.drawImage(DopoSoccerGUI.GAME_BACKGROUND, 0, 0, getWidth(), getHeight(), this);   // Escalada al tamaño del panel
    }

    public void drawPlayer(Graphics g, int player){
        int size = game.getPlayerSize(player);
        g.setColor(Color.WHITE);
        g.fillOval(game.getPlayerX(player), game.getPlayerY(player), size, size);
    }

    public void drawField(Graphics g){
        g.setColor(Color.WHITE);
        for(int side = Match.LEFT_SIDE; side <= Match.RIGHT_SIDE; side++){
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

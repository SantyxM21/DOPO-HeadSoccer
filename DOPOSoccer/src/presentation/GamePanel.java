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
    private Image background;
    private Graphics graphics;
    private DopoSoccer game;
    private KeyHandler keyH = new KeyHandler();

    public GamePanel(DopoSoccer game){
        super();
        this.game = game;
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        background = new ImageIcon(getClass().getResource("/resources/fondo_dopo_soccer.png")).getImage();
        setFocusable(true);                              
    }

    public JPanel prepareGamePage(){
        
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
        if(!game.hasMatch()) return;    

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
        repaint();
   }

    @Override
    // Este metodo es llamado por setVisible(true) o tambien por repaint(), y muchos mas.
    public void paint(Graphics g){                  
        image = createImage(getWidth(),getHeight());     // Crea un lienzo vacio de height*width
        graphics = image.getGraphics();                  // Obtiene acceso para pintar en el lienzo (pincel)
        draw(graphics);                                  // Le da el pincel a todos los que quiera dibujar en el lienzo 
        g.drawImage(image, 0, 0, this);             // Copia la imagen ya terminada a la pantalla, desde la esquina superior izquierda (0, 0)
    }

    public void draw(Graphics g){
        drawBackground(g);
        if(!game.hasMatch()) return;   
        drawField(g);                             // Primero el fondo para que quede detras de todo
        drawPlayer(g, 1);
        drawPlayer(g, 2);
        drawBall(g);

        //No olviden agregar el marcador aca
    }

    public void drawBackground(Graphics g){
        if(background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);   // Escalada al tamaño del panel
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

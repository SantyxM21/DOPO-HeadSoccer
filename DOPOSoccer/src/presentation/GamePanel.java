package presentation;

import java.awt.*;
import javax.swing.*;

import domain.DopoSoccer;
import domain.Match;
import domain.Player;

public class GamePanel extends JPanel{
    private final Dimension GAME_SIZE;

    private Image image;
    private Image background;
    private Graphics graphics;
    private DopoSoccer game;

    public GamePanel(DopoSoccer game){
        super();
        this.game = game;
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        background = new ImageIcon(getClass().getResource("/resources/fondo_dopo_soccer.png")).getImage();
    }

    public JPanel prepareGamePage(){
        
        return this;
    } 

    public void prepareController(MainPanel panel){

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
        drawBackground(g);                               // Primero el fondo para que quede detras de todo
        drawPlayer(g, 1);
        drawPlayer(g, 2);
    }

    public void drawBackground(Graphics g){
        if(background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);   // Escalada al tamaño del panel
    }

    public void drawPlayer(Graphics g, int player){
        g.setColor(Color.WHITE);
        g.fillOval(game.getPlayerX(player), game.getPlayerY(player), Player.PLAYER_SIZE, Player.PLAYER_SIZE);
    }
}

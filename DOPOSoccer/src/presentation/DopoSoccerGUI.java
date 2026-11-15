package presentation;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Write a description of class DopoSoccerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DopoSoccerGUI extends JFrame{

    private static final Image img = loadImage("/resources/logo_icon.png");

    public static final Image PANEL_BACKGROUND = loadImage("/resources/fondo_paneles_dopo_soccer.png");
    public static final Image GAME_BACKGROUND = loadImage("/resources/fondo_dopo_soccer.png");

    private final MainPanel main = new MainPanel();

    public DopoSoccerGUI(){
        this.add(main);                                     //Se añade el panel al frame
        this.setTitle("DOPO Soccer");
        this.setResizable(false);                           //No permite modificar el tamaño de la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setBackground(Color.BLACK);
        this.pack();                                        // Toma las dimensiones del panel que se añadio 
        this.setIconImage(img);
        this.setLocationRelativeTo(null);                   //Centra la ventana
        this.setVisible(true);  //Inicio h
    }

    public static Image loadImage(String path){
        try {
            return ImageIO.read(DopoSoccerGUI.class.getResource(path));
        } catch (IOException | IllegalArgumentException e) {
            return null;
        }
    }

    public static JLabel createLogo(int heightRate){
        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        Image imgLogo = DopoSoccerGUI.loadImage("/resources/logo_dopo_soccer2.png");
        if (imgLogo != null) {
            logo.setIcon(new ImageIcon(imgLogo.getScaledInstance(-1, MainPanel.GAME_SIZE.height / (heightRate/2), Image.SCALE_SMOOTH)));
        } else {
            logo.setText("<html><center>DOPO<br>SOCCER</center></html>");
            logo.setFont(new Font("Arial", Font.BOLD, MainPanel.GAME_SIZE.height / heightRate));
        }
        return logo;
    }

    public static void main(String[] args) {
        DopoSoccerGUI gui = new DopoSoccerGUI();
    }
}

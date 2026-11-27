package presentation;

import domain.DopoSoccerException;
import domain.Log;
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

    /**
     * Loads an image from the specified path.
     * @param path the path to the image file
     * @return the loaded image or null if not found
     */
    public static Image loadImage(String path){
        try {
            return ImageIO.read(DopoSoccerGUI.class.getResource(path));
        } catch (IOException | IllegalArgumentException e) {
            Log.record(new DopoSoccerException(DopoSoccerException.IMAGE_NOT_FOUND + " " + path, e));   
            return null;
        }
    }

    /**
     * Loads a flag image for the specified country and scales it to the given height.
     * @param country the country code or name
     * @param height the desired height of the flag image
     * @return an ImageIcon of the flag or null if not found
     */
    public static ImageIcon flagIcon(String country, int height){
        Image flag = loadImage("/resources/" + country + ".png");
        if (flag == null) return null;
        return new ImageIcon(flag.getScaledInstance(-1, height, Image.SCALE_SMOOTH));   // ancho -1 mantiene la proporcion
    }

    /**
     * Displays a flag image for the specified country in the given label.
     * @param label the label where the flag will be displayed
     * @param country the country code or name
     * @param height the desired height of the flag image
     */
    public static void showFlag(JLabel label, String country, int height){
        ImageIcon icon = flagIcon(country, height);
        label.setIcon(icon);
        label.setText(icon == null ? country : null);      // texto solo si no hay bandera
    }

    /**
     * Displays a flag image for the specified country in the given button.
     * @param button the button where the flag will be displayed
     * @param country the country code or name
     * @param height the desired height of the flag image
     */
    public static void showFlag(AbstractButton button, String country, int height){
        ImageIcon icon = flagIcon(country, height);
        button.setIcon(icon);
        button.setText(icon == null ? country : null);     // texto solo si no hay bandera
    }

    /**
     * Creates a logo label with the specified height rate.
     * @param heightRate the rate for determining the logo height
     * @return a JLabel containing the logo
     */
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

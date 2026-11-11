package presentation;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PruebaSprites extends JPanel{
    private BufferedImage spritePrueba;
    private int x = 100;
    private int y = 100;

    public PruebaSprites(){
        try{
            spritePrueba = ImageIO.read(getClass().getResourceAsStream("/resources/Character01.jpg"));
        } catch (IOException | IllegalArgumentException e){
            System.err.println("No se pudo cargar el sprite.");
            e.printStackTrace();
        }
    }

     @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (spritePrueba != null) {
            // Dibuja el sprite en las coordenadas (x, y)
            g2d.drawImage(spritePrueba, x, y, null);
            
            //  para redimensionarlo
            // g2d.drawImage(spritePrueba, x, y, anchoNuevo, altoNuevo, null);
        }
    }


}

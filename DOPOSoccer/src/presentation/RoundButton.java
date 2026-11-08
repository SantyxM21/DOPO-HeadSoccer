package presentation;

import java.awt.*;
import javax.swing.*;


public class RoundButton extends JButton {

    public RoundButton() {
        this.setContentAreaFilled(false);                   
        this.setBorderPainted(false);                       // quita el borde
        this.setFocusPainted(false);                        // quita el recuadro de foco al hacer clic
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));     // cursor de mano al pasar sobre el botón
        int side = MainPanel.GAME_SIZE.height / 4;          // lado que se adapta a la pantalla
        this.setPreferredSize(new Dimension(side, side));   
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int stroke = 2;                                         // grosor del borde
        int size = Math.min(getWidth(), getHeight()) - stroke;  
        int x = (getWidth() - size) / 2;                        // posición X para centrar el círculo
        int y = (getHeight() - size) / 2;                       // posición Y para centrar el círculo

        g2.setColor(getModel().isRollover() ? new Color(235, 235, 235) : Color.WHITE);  // gris claro al pasar el mouse, blanco normal
        g2.fillOval(x, y, size, size);                          // relleno del círculo
        g2.setColor(Color.BLACK);                               // color del borde
        g2.setStroke(new BasicStroke(stroke));                  // aplica el grosor del borde
        g2.drawOval(x, y, size, size);                          // contorno del círculo

        g2.dispose();

        super.paintComponent(g);                                
    }
}

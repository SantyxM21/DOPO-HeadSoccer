package presentation;

import java.awt.*;
import javax.swing.*;

public class CustomButton extends JButton {

    private int radius = 30;
    private int borderWidth = 3;
    private Color normal = Color.WHITE;

    public CustomButton(String text) {
        super(text);
        init();
    }

    private void init(){
        this.setFocusPainted(false);                        // quita el recuadro de foco al hacer clic
        this.setContentAreaFilled(false);                   
        this.setBorderPainted(false);                       // quita el borde
        this.setOpaque(false);                              // permite esquinas redondeadas transparentes
        this.setForeground(Color.BLACK);                    
        this.setFont(new Font("Arial", Font.BOLD, MainPanel.GAME_SIZE.height / 30));   // tamaño de letra que se adapta
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));     // cursor de mano al pasar sobre el botón
        this.setMargin(new Insets(10, 30, 10, 30));         // margen alrededor del texto
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Suavizado de bordes

        if (getModel().isPressed())       g2.setColor(normal.darker());
        else if (getModel().isRollover()) g2.setColor(new Color(235, 235, 235));
        else                              g2.setColor(normal);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(borderWidth));
        int half = borderWidth / 2;
        g2.drawRoundRect(half, half, getWidth() - borderWidth, getHeight() - borderWidth, radius, radius);
        g2.dispose();

        super.paintComponent(g);    
    }
}

package presentation;

import java.awt.*;
import javax.swing.*;

public class CustomButton extends JButton {

    private int radius = 30;
    private int borderWidth = 2;
    private Color normal = Color.WHITE;

    public CustomButton(String text) {
        super(text);
        init();
    }

    /*
    This method creates a "Volver" button with the same height on every page
    @return the back button
    */
    public static CustomButton createBackButton(){
        CustomButton button = new CustomButton("Volver");
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, MainPanel.GAME_SIZE.height / 12));
        return button;
    }

    /*
    This method wraps a component so BorderLayout does not stretch it to the height of the bar
    @param component the component to wrap
    @return a transparent panel that keeps the component's preferred size
    */
    public static JPanel keepSize(JComponent component){
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(component);
        return wrapper;
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

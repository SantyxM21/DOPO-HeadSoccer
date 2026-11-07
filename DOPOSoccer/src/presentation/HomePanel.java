package presentation;

import java.awt.*;
import javax.swing.*;

public class HomePanel extends JPanel{

    private final Dimension GAME_SIZE;

    public HomePanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;
    }

    /* 
    This method prepares the home page panel 
    @return the prepared home page panel
    */
    public JPanel prepareHomePage(){

        JLabel gameName = new JLabel("<html><center>POOB<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 7));          // Tamaño de letra que se adapta
        gameName.setHorizontalAlignment(SwingConstants.CENTER);
        gameName.setBorder(BorderFactory.createEmptyBorder(0, 0, GAME_SIZE.height / 10, 0));   // margen inferior: sube el texto


        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 10, GAME_SIZE.height / 32));
        buttons.setOpaque(false);   
        buttons.add(createModeButton("Tiempo"));
        buttons.add(createModeButton("Goles"));

        RoundButton charactersButton = new RoundButton();
        int side = GAME_SIZE.height / 6;                                                // más pequeño que los botones de modo
        charactersButton.setPreferredSize(new Dimension(side, side));
        charactersButton.setText("Abrir");
        charactersButton.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 30));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, GAME_SIZE.width / 40, GAME_SIZE.height / 40));
        topBar.setOpaque(false);
        topBar.add(charactersButton);                                                   // esquina superior derecha

        this.add(topBar, BorderLayout.NORTH);
        this.add(gameName, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);

        return this;
    }

    /* 
    This method creates a mode button with the given text 
    @param text the text for the button
    @return the JPanel that contains the button and its label
    */
    private JPanel createModeButton(String text){
        JPanel panel = new JPanel(new BorderLayout(0, 1));
        panel.setOpaque(false);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 35));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        panel.add(new RoundButton(), BorderLayout.CENTER);  // Se añade el botón redondo al panel en el centro
        panel.add(label, BorderLayout.SOUTH);               // Se añade la etiqueta debajo del botón
        return panel;
    }
}

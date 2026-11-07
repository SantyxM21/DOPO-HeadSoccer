package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoadingPanel extends JPanel{
    
    private final Dimension GAME_SIZE;

    private CustomButton fullScreenButton;
    private CustomButton mediumScreeButton;
    private CustomButton smallScreeButton;

    public LoadingPanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;

        fullScreenButton = new CustomButton("Full Screen");
        mediumScreeButton = new CustomButton("Medium");
        smallScreeButton = new CustomButton("Small");
    }

    /* This method prepares the loading page */
    public JPanel prepareLoadingPage(){

        JLabel gameName = new JLabel("<html><center>DOPO<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height/6));           // Tamaño de letra que se adapta
        gameName.setHorizontalAlignment(SwingConstants.CENTER);
        gameName.setVerticalAlignment(SwingConstants.CENTER);

        JPanel resolutionOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 10, GAME_SIZE.height / 25)); 
        resolutionOptions.add(fullScreenButton);
        resolutionOptions.add(mediumScreeButton);
        resolutionOptions.add(smallScreeButton);

        this.add(gameName, BorderLayout.CENTER);
        this.add(resolutionOptions, BorderLayout.SOUTH);

        return this;
    }

    public void prepareController(MainPanel main){
        fullScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.changeScale(1.1);
            }
        });

        mediumScreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.changeScale(1.5);
            }
        });

        smallScreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.changeScale(1.7);
            }
        });
    }
}

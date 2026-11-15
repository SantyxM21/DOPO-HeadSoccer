package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

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

        JLabel gameName = DopoSoccerGUI.createLogo(6);

        JPanel resolutionOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 10, GAME_SIZE.height / 25)); 
        resolutionOptions.setOpaque(false);
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
                main.changeScale(1.4);
            }
        });

        smallScreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.changeScale(1.7);
            }
        });
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (DopoSoccerGUI.GAME_BACKGROUND != null) g.drawImage(DopoSoccerGUI.GAME_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}

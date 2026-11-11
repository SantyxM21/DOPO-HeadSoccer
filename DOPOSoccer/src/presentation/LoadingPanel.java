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

        JLabel gameName = createLogo();

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

    public JLabel createLogo(){
        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        try {
            Image imgLogo = new ImageIcon(getClass().getResource("/resources/logo_dopo_soccer2.png")).getImage()
                                            .getScaledInstance(-1, GAME_SIZE.height / 3, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(imgLogo));
        } catch (Exception e) {
            logo.setText("<html><center>DOPO<br>SOCCER</center></html>");
            logo.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 6));
        }
        return logo;
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon img = new ImageIcon(getClass().getResource("/resources/fondo_dopo_soccer.png"));
        g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}

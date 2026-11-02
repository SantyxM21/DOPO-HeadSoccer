package presentation;

import java.awt.*;
import javax.swing.*;


/**
 * Write a description of class MainPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainPanel extends JPanel{

    public static final Dimension FULL_SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int FULL_WIDTH = FULL_SCREEN_SIZE.width;
    public static final int FULL_HEIGHT = FULL_SCREEN_SIZE.height;
    public static final Dimension GAME_SIZE =  new Dimension((int)(FULL_WIDTH/1.4), (int)(FULL_HEIGHT/1.4));
    
    private final CardLayout cards;
    private JPanel loadingPage;
    
    public MainPanel(){
        super(new CardLayout());
        this.cards = (CardLayout) getLayout();

        this.setPreferredSize(GAME_SIZE);       // El tamaño se adapta segun la pantalla donde se ejecute
        this.prepareView();
    }

    /* This method prepares the view of the main panel */
    public void prepareView(){
        prepareLoadingPage();
    }
    
    /* This method prepares the loading page */
    public void prepareLoadingPage(){
        loadingPage = new JPanel(new BorderLayout());
        this.add(loadingPage, "Loading Page");

        JLabel gameName = new JLabel("<html><center>POOB<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, FULL_HEIGHT/6));           // Tamaño de letra que se adapta
        gameName.setHorizontalAlignment(SwingConstants.CENTER);
        gameName.setVerticalAlignment(SwingConstants.CENTER);
        
        JLabel infoText = new JLabel("Presione ENTER para continuar");
        infoText.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 25));
        infoText.setHorizontalAlignment(SwingConstants.CENTER);
        infoText.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));               // margen inferior
        
        loadingPage.add(gameName, BorderLayout.CENTER);
        loadingPage.add(infoText, BorderLayout.SOUTH);
    }
}

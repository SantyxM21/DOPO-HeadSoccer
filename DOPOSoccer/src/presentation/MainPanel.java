package presentation;

import java.awt.*;
import java.awt.event.*;
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
    
    private String currentPage = "Loading Page";
    private final CardLayout cards;
    private JPanel loadingPage;
    private HomePanel homePage;
    
    public MainPanel(){
        super(new CardLayout());
        this.cards = (CardLayout) getLayout();
        this.setFocusable(true);
        this.setPreferredSize(GAME_SIZE);       // El tamaño se adapta segun la pantalla donde se ejecute

        homePage = new HomePanel();

        this.prepareView();
        this.prepareController();
    }

    /* This method prepares the view of the main panel */
    private void prepareView(){
        prepareLoadingPage();
        this.add(homePage.prepareHomePage(), "Home menu");
    }
    
    /* This method prepares the loading page */
    private void prepareLoadingPage(){
        loadingPage = new JPanel(new BorderLayout());
        this.add(loadingPage, "Loading Page");

        JLabel gameName = new JLabel("<html><center>DOPO<br>SOCCER</center></html>");
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

    private void prepareController(){
        addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e){
                keyBoardEvent(e);
            }
        });
    }

    /* 
    This method handles keyboard events 
    @param e the KeyEvent to handle
    */
    private void keyBoardEvent(KeyEvent e){
        // Funciona solo si se esta en la pagina de carga y se presiona ENTER
        if (currentPage.equals("Loading Page") && e.getKeyCode() == KeyEvent.VK_ENTER){
            showPage("Home menu");
        }
    }

    /* 
    This method shows the page with the given name 
    @param name the name of the page to show
    */
    private void showPage(String name){
        cards.show(this, name);
        currentPage = name;
    }
}

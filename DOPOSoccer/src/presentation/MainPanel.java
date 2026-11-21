package presentation;

import domain.DopoSoccer;
import domain.DopoSoccerException;
import domain.Machine;
import domain.Match;
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
    public static Dimension GAME_SIZE;   

    private final CardLayout cards;
    private LoadingPanel loadingPage;
    private HomePanel homePage;
    private ModePanel modePage;
    private SelectionPanel selectionPage;
    private CharactersInfoPanel charactersPage;
    private GamePanel gamePage;
    private int matchMode;      // 1 time, 2 goals
    private int playersMode;    // 1 player vs player , 2 player vs machine
    private int confingMode;
    private DopoSoccer game;


    public MainPanel(){
        super(new CardLayout());
        GAME_SIZE = sizeFor(1.4);
        this.cards = (CardLayout) getLayout();
        this.setFocusable(true);
        this.setPreferredSize(GAME_SIZE);       // El tamaño se adapta segun la pantalla donde se ejecute

        game = new DopoSoccer();                
        homePage = new HomePanel();
        loadingPage = new LoadingPanel();
        modePage = new ModePanel();
        selectionPage = new SelectionPanel();
        charactersPage = new CharactersInfoPanel();
        gamePage = new GamePanel(game);

        this.prepareView();
        this.prepareController();
    }

    /* This method prepares the view of the main panel */
    private void prepareView(){
        this.add(loadingPage.prepareLoadingPage(), "Loading Page");
        this.add(homePage.prepareHomePage(), "Home menu");
        this.add(modePage.prepareModePage(), "Mode menu");
        this.add(selectionPage.prepareSelectionPage(), "Selection menu");
        this.add(charactersPage.prepareCharacterPage(), "Character page");
        this.add(gamePage.prepareGamePage(), "Game");
    }

    /* This method prepares the controller for the main panel */
    private void prepareController(){
        loadingPage.prepareController(this);
        homePage.prepareController(this);
        modePage.prepareController(this);
        selectionPage.prepareController(this);
        charactersPage.prepareController(this);
        gamePage.prepareController(this);
    }

    /* This method calculates the size for a given divisor */
    private Dimension sizeFor(double divisor){
        return new Dimension((int)(FULL_WIDTH/divisor), (int)(FULL_HEIGHT/divisor));
    }

    /* Changes the game size and rebuilds the pages */
    public void changeScale(double divisor){
        GAME_SIZE = sizeFor(divisor);
        this.setPreferredSize(GAME_SIZE);

        gamePage.stopGameLoop();            
        this.removeAll();                   // Quita todos los componentes de este container/panel
        homePage = new HomePanel();
        loadingPage = new LoadingPanel();
        modePage = new ModePanel();
        selectionPage = new SelectionPanel();
        charactersPage = new CharactersInfoPanel();
        gamePage = new GamePanel(game);
        this.prepareView();
        this.prepareController();

        Window window = SwingUtilities.getWindowAncestor(this);     // Devuelve el JFrame que contiene este panel
        if (window != null) {
            window.pack();
            window.setLocationRelativeTo(null);     // Centra la ventana en la pantalla con el nuevo tamaño
        }

        showPage("Home menu");
    }

    public boolean startGame(String leftCountry, String rightCountry) {
        try {
            if(DopoSoccer.PVSP_MODE == playersMode){
                game.newGame(matchMode, String.valueOf(confingMode), GAME_SIZE);   // el dominio recibe la condicion como String y la valida
                game.addHuman(Match.LEFT_PLAYER, leftCountry, game.getProfile(Match.LEFT_PLAYER));
                game.addHuman(Match.RIGHT_PLAYER, rightCountry, game.getProfile(Match.RIGHT_PLAYER));
            }
            else if(DopoSoccer.PVSM_MODE == playersMode){
                game.newGame(matchMode, String.valueOf(confingMode), GAME_SIZE);   
                game.addHuman(Match.LEFT_PLAYER, leftCountry, game.getProfile(Match.LEFT_PLAYER));
                game.addMachine(Match.RIGHT_PLAYER, rightCountry, game.getProfile(Match.RIGHT_PLAYER), Machine.EXPERT); // Machine dumb temporal
            }else  return false;
            
            gamePage.setCountries(leftCountry, rightCountry);
            return true;
        } catch (DopoSoccerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    /* 
    This method shows the page with the given name 
    @param name the name of the page to show
    */
    public void showPage(String name){
        cards.show(this, name);
        if(name.equals("Game")) gamePage.requestFocusInWindow();
    }

    public static void funcUnderConstruction(String ms){
        JOptionPane.showMessageDialog(null, ms);
    }

    public void setMatchMode(int mode){
        this.matchMode = mode;
    }

    public void setPlayersMode(int mode){
        this.playersMode = mode;
    }

    public void setConfigMode(int configuration){
        this.confingMode = configuration;
    }

    public void updateInfoPlayer(int side, String shownChar){
        String playerName = game.getPlayerName(side);
        int playerPoints = game.getPlayerPoints(side);
        int[] playerStats = game.getPlayerStats(side);

        charactersPage.updateInfo(playerName, playerPoints, playerStats, side, shownChar);
    }

    public void upgradeStat(int side, int stat) throws DopoSoccerException{
        game.upgradeStats(side, stat);
    }

    public String getPlayerName(int side){
        return game.getPlayerName(side);
    }

    public void swapProfiles(){
        game.swapProfiles();
    }
}

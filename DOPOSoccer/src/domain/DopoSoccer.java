package domain;

import java.awt.Dimension;
import java.util.*;

/**
 * 
 * 
 * @author Ivan Andres Bernal Sabogal
 * @author César Santiago Malaver Garnica
 * @version 05/11/2026
 */
public class DopoSoccer {

    public static final int TIME_MODE = 1;
    public static final int GOALS_MODE = 2; 
    public static final int PVSP_MODE = 1; 
    public static final int PVSM_MODE = 2; 

    private Game game;
    private HashMap<String, Character_> characters;

    //condition es el time o numero de goles
    /**
     * 
     * @param matchMode 1 time, 2 goals
     * @param playersMode 1 player vs player , 2 player vs machine
     * @param machineType dumb, offesive, defensive or expert. Null if mode PvsP
     * @param leftCharacter country of the left player
     * @param rightCharacter country of the right player
     * @param condition time or goals, depends of the match mode
     * @throws DopoSoccerException
     */
    public DopoSoccer(int matchMode, int playersMode, String machineType, String leftCharacter, String rightCharacter, String condition,Dimension resolution ) throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.UNDER_CONSTRUCTION);
    }

    public void startGame() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
    /**
     * 
     * @param Player the player who does the action
     * @param Action move, jump, kick or active power
     * @throws DopoSoccerException
     */
    public void playerAction(int player, String action) throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public Match getMatch() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}
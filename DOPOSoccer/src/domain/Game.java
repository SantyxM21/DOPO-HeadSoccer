package domain;
import java.util.*;

public class Game {
    private GameMode gameMode;
    private ArrayList<Match> matches;
    private Player leftPlayer;
    private Player rightPlayer;

    public Game(GameMode gameMode, Player leftPlayer, Player rightPlayer) throws DopoSoccerException {
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void startMatch() throws DopoSoccerException {
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void playerAction(int player, String action) throws DopoSoccerException {
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public Match getMatch() throws DopoSoccerException {
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

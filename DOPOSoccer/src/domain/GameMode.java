package domain;

/**
 * 
 * GameMode
 */
public abstract class GameMode {
    

    public abstract boolean isMatchFinished(Player left, Player right, double secondsElapsed);

    public abstract Player getWinner(Player left, Player right, double secondsElapsed);
}

package domain;

public class GoalsMatch extends GameMode {
    private int goalsToWin;

    public GoalsMatch(int goalsToWin) throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    @Override 
    public boolean isMatchFinished(Player left, Player right, double secondsElapsed){
        return true;
    }

    public Player getWinner(Player left, Player right, double secondsElapsed){
        return left;
    }

    public int getGoalsToWin(Player left, Player right, double secondsElapsed){
        return goalsToWin;
    }
}

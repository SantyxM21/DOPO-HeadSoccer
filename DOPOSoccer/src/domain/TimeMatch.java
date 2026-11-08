package domain;

public class TimeMatch extends GameMode {
    private double seconds;

    public TimeMatch(double seconds) throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    @Override 
    public boolean isMatchFinished(Player left, Player right, double secondsElapsed){
        return true;
    }

    public Player getWinner(Player left, Player right, double secondsElapsed){
        return left;
    }

    public boolean isSuddenDeath(Player left, Player right, double secondsElapsed){
        return false;
    }
    public double getTime(){
        return seconds;
    }
}

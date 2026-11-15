package domain;

import java.awt.Dimension;

public class TimeMatch extends Match{
    private double secondsLeft;
    private long lastUpdatedFrame;
    
    public TimeMatch(String condition, Dimension resolution) throws DopoSoccerException{
        super(condition, resolution);
    }

    @Override
    public void setCondition(String condition) throws DopoSoccerException{
        double minutes;
        try {
            minutes = Double.parseDouble(condition.replace(",", "."));
        } catch (NumberFormatException | NullPointerException e) {
            throw new DopoSoccerException(DopoSoccerException.INVALID_TIME);
        }
        if (minutes <= 0) throw new DopoSoccerException(DopoSoccerException.INVALID_TIME);
        secondsLeft = minutes * 60;
    }

    @Override
    protected void checkFinished(){
        long now = System.currentTimeMillis();
        if (lastUpdatedFrame != 0) advanceTime((now - lastUpdatedFrame) / 1000.0); //conversion a sdegundos
        lastUpdatedFrame = now;
    }

    public void advanceTime(double seconds){
        secondsLeft = Math.max(0, secondsLeft - seconds);
        if (secondsLeft > 0) return;

        int leftGoals = getPlayer(LEFT_SIDE).getGoals();
        int rightGoals = getPlayer(RIGHT_SIDE).getGoals();

        //Se termina con ganador, muerte subita no
        if (leftGoals > rightGoals) finish(LEFT_SIDE);
        else if (rightGoals > leftGoals) finish(RIGHT_SIDE);
    }

    public int getSecondsLeft(){
        return (int) Math.ceil(secondsLeft);
    }

    public boolean isSuddenDeath(){
        return secondsLeft == 0 && !isFinished();
    }
}
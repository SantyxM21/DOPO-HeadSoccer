package domain;

import java.awt.Dimension;

public class TimeMatch extends Match{
    private int durationSecs;      

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
        durationSecs = (int) Math.ceil(minutes * 60);
    }

    @Override
    protected void checkFinished(){
        if (getSecondsLeft() > 0) return;

        int leftGoals = getPlayer(LEFT_SIDE).getGoals();
        int rightGoals = getPlayer(RIGHT_SIDE).getGoals();

        //Se termina con ganador, muerte subita no
        if (leftGoals > rightGoals) finish(LEFT_SIDE);
        else if (rightGoals > leftGoals) finish(RIGHT_SIDE);
    }

    public int getSecondsLeft(){
        return Math.max(0, durationSecs - getTime());
    }

    public boolean isSuddenDeath(){
        return getSecondsLeft() == 0 && !isFinished();
    }

    @Override 
    public String textUnderScore(){
        if (isSuddenDeath()) return "MUERTE SUBITA";
        else{
            int seconds = getSecondsLeft();
            return String.format("%d:%02d", seconds / 60, seconds % 60); //Formato 1:10 
        }
    } 
}
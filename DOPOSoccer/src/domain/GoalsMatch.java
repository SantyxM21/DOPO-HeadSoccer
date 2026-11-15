package domain;

import java.awt.Dimension;

public class GoalsMatch extends Match{
    private int numGoals;
    
    public GoalsMatch(String condition, Dimension resolution) throws DopoSoccerException{
        super(condition, resolution);
    }
    
    @Override 
    public void setCondition(String condition) throws DopoSoccerException{
        try {
            numGoals = Integer.parseInt(condition);
        } catch (NumberFormatException e) {
            throw new DopoSoccerException(DopoSoccerException.INVALID_GOALS);
        }
         if (numGoals <= 0) throw new DopoSoccerException(DopoSoccerException.INVALID_GOALS);
    }

    @Override 
    protected void checkFinished(){
        int leftGoals = getPlayer(LEFT_SIDE).getGoals();
        int rightGoals = getPlayer(RIGHT_SIDE).getGoals();

        if (leftGoals >= numGoals) finish(LEFT_SIDE); 
        else if(rightGoals >= numGoals) finish(RIGHT_SIDE);
    }

    public int getNumGoals(){
        return numGoals;
    }

    @Override 
    public String textUnderScore(){
        return "Goles para ganar " + numGoals;
    }

}
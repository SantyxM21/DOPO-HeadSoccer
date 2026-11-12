package domain;

import java.awt.Dimension;

public class GoalsMatch extends Match{
    private int numGoals;
    
    public GoalsMatch(String condition, Dimension resolution) {
        super(condition, resolution);
    }
    
    public void setCondition(String condition){
        try {
            numGoals = Integer.parseInt(condition);
        } catch (NumberFormatException e) {
            // TODO
        }
    }
}

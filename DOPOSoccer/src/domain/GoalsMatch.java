package domain;

import java.awt.Dimension;

public class GoalsMatch extends Match{
    private int numGoals;
    
    public GoalsMatch(int condition, Dimension resolution) {
        super(condition, resolution);
    }

    public void setCondition(int condition){
        numGoals = condition;
    }
}

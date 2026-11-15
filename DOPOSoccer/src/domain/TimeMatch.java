package domain;

import java.awt.Dimension;

public class TimeMatch extends Match {
    private double seconds;
    
    public TimeMatch(int condition, Dimension resolution) {
        super(condition, resolution);
    }

    public void setCondition(int condition){
        seconds = condition;
    }
}

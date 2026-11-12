package domain;

import java.awt.Dimension;

public class TimeMatch extends Match {
    private double seconds;
    
    public TimeMatch(String condition, Dimension resolution) {
        super(condition, resolution);
    }

    public void setCondition(String condition){
        try {
            seconds = Double.parseDouble(condition.replace(",", "."));
        } catch (NumberFormatException e) {
            // TODO
        }
    }
}

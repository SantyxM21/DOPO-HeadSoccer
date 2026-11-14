package domain;

import java.awt.Dimension;

public class Field {
    private GoalFrame leftGoalFrame;
    private GoalFrame rightGoalFrame;

    //Aun por definir posiciones en donde seran creados
    public Field(Dimension resolution){
        leftGoalFrame = new GoalFrame(0, 0);
        rightGoalFrame = new GoalFrame(0, 0);
    }
}

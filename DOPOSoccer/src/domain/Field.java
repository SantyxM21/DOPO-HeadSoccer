package domain;

import java.awt.Dimension;

public class Field {
    public static final double GROUND_MARGIN = 0.15; //Margen que se le va a dejar al pasto
    private static final double REFERENCE_HEIGHT = 720; //Altura base
    private static final double GOAL_WIDTH_RANGE = 0.0; //Por definir
    private static final double GOAL_HEIGHT_RANGE = 0.0; //Por definir

    private GoalFrame leftGoalFrame;
    private GoalFrame rightGoalFrame;

    private final int width;
    private final int height;
    private final int groundY;
    private final double scale;


    //Aun por definir posiciones en donde seran creados
    public Field(Dimension resolution){
        this.width = resolution.width;
        this.height = resolution.height;
        this.groundY = (int) (height * (1 - GROUND_MARGIN)); //Posicion en donde se encuentra la linea del pasto
        this.scale = height / REFERENCE_HEIGHT;

        int goalWidth = (int) (width * GOAL_WIDTH_RANGE);
        int goalHeight = (int) (height * GOAL_HEIGHT_RANGE);

        leftGoalFrame = new GoalFrame(0, groundY - goalHeight, goalWidth, goalHeight); //Ubica la referencia de arco izquierdo
        rightGoalFrame = new GoalFrame(width - goalWidth, groundY - goalHeight, goalWidth, goalHeight); //Ubica la referencia del arco derecho
    }

    public double scale(double base){
        return base * scale;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getGroundY(){
        return groundY;
    }

    public GoalFrame getGoalFrame(int side){
        if (side == Match.LEFT_SIDE) return leftGoalFrame;
        else return rightGoalFrame;
    }
}

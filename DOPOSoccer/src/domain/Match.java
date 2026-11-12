package domain;

import java.awt.Dimension;

public abstract class Match {
    public static final int LEFT_SIDE = 1;
    public static final int RIGHT_SIDE = 2;
    private static final double START_X_RATIO = 0.2;       
    private static final double GROUND_MARGIN_RATIO = 0.15;  
    public final Dimension RESOLUTION;

    private Player leftPlayer;
    private Player rightPlayer;
    private Field field;
    private Ball ball;

    public Match(String condition, Dimension resolution){
        setCondition(condition);
        this.RESOLUTION = resolution;
    }

    public abstract void setCondition(String condition);

    public void addHuman(int side, String character){
        if(side == LEFT_SIDE) leftPlayer = new Human(character, startX(side), startY());
        else if(side == RIGHT_SIDE) rightPlayer = new Human(character, startX(side), startY());
    }

    private double startX(int side){
        double margin = RESOLUTION.getWidth() * START_X_RATIO;
        if(side == LEFT_SIDE) return margin;
        return RESOLUTION.getWidth() - margin - Player.PLAYER_SIZE;
    }

    private double startY(){
        double groundMargin = RESOLUTION.getHeight() * GROUND_MARGIN_RATIO;
        return RESOLUTION.getHeight() - groundMargin - Player.PLAYER_SIZE;
    }

    public Player getPlayer(int side){
        if(side == LEFT_SIDE) return leftPlayer;
        else if(side == RIGHT_SIDE) return rightPlayer;

        return null; // TODO: Manejar como expecion no retornar null
    }


}

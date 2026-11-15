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
        this.field = new Field(resolution);
        this.ball = new Ball(field); // El balon se va a crear en el centro del campo
    }

    public abstract void setCondition(String condition);

    public void addHuman(int side, String character, Profile profile) throws DopoSoccerException{
        if(side == LEFT_SIDE) {
            leftPlayer = new Human(character, startX(side), startY(), field);
            leftPlayer.setProfile(profile);
        }
        else if (side == RIGHT_SIDE){
            rightPlayer = new Human(character, startX(side), startY(), field);
            rightPlayer.setProfile(profile);
        }
        else throw new DopoSoccerException(DopoSoccerException.INVALID_SITE);
    }

    private double startX(int side){
        double margin = field.getWidth() * START_X_RATIO;
        if(side == LEFT_SIDE) return margin;
        return field.getWidth() - margin - playerSize();
    }

    private double startY(){
        return field.getGroundY() - playerSize();
    }

    public void update(){
        leftPlayer.applyGravity();
        rightPlayer.applyGravity();
        ball.move();
    }

    public Player getPlayer(int side){
        if(side == LEFT_SIDE) return leftPlayer;
        else if(side == RIGHT_SIDE) return rightPlayer;

        else throw new IllegalArgumentException("Lado inválido: " + side);
    }

    private int playerSize(){
        return (int) field.scale(Player.PLAYER_SIZE);
    }

    public Field getField(){
        return field;
    }

    public Ball getBall(){
        return ball;
    }


}

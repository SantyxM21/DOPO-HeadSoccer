package domain;

import java.io.Serializable;

public abstract class CircularEntity implements Serializable{
    public static final double GRAVITY = 0.15;

    protected final Field field;
    protected final int diameter;
    protected final double gravity;
    protected double xPosition;
    protected double yPosition;
    protected Vector2D velocity;

    public CircularEntity(double x, double y, Field field, int baseDiameter){
        this.field = field;
        this.diameter = (int) field.scale(baseDiameter);
        this.gravity = field.scale(GRAVITY);
        xPosition = x;
        yPosition = y;
        velocity = new Vector2D(0, 0);
    }

    public double radius(){
        return diameter / 2.0;
    }

    public Vector2D center(){
        return new Vector2D(xPosition + radius(), yPosition + radius());
    }

    public boolean isTouching(CircularEntity other){
        double length = Vector2D.length(Vector2D.subtract(other.center(), center()));
        return length < radius() + other.radius();
    }

    public int getDiameter(){
        return diameter;
    }

    public int getXPosition(){
        return (int) xPosition;
    }

    public int getYPosition(){
        return (int) yPosition;
    }

    public void setXPosition(double x){
        xPosition = x;
    }

    public void setYPosition(double y){
        yPosition = y;
    }

    public Vector2D getVelocity(){
        return new Vector2D(velocity.getX(), velocity.getY());
    }

    public void setVelocity(Vector2D velocity){
        this.velocity = new Vector2D(velocity.getX(), velocity.getY());
    }

    public void setPositions(Vector2D positions){
        setXPosition(positions.getX());
        setYPosition(positions.getY());
    }
}

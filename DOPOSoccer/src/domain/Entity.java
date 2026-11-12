package domain;

public abstract class Entity {
    protected double xPosition;
    protected double yPosition;
    
    public Entity(double x, double y){
        xPosition = x;
        yPosition = y;
    }

    public int getXPosition(){
        return (int) xPosition;
    }

    public int getYPosition(){
        return (int) yPosition;
    }
}

package domain;

public class Ball extends Entity{
    private Power chargedPower;
    private Player chargedBy;

    private double xPosition;
    private double yPosition;

    private int diameter;

    public Ball(int xPositon, int yPositon, int diameter){
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.diameter = diameter;
    }

    
}

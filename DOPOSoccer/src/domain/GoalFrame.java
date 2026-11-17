package domain;

public class GoalFrame {
    private final double xPosition;
    private final double yPosition;
    private final int width;
    private final int height;

    public GoalFrame(double x, double y, int width, int height){
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    public int getXPosition(){
        return (int) xPosition;
    }

    public int getYPosition(){
        return (int) yPosition;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

package domain;

public class GoalFrame extends Entity {
    private final int width;
    private final int height;

    public GoalFrame(double x, double y, int width, int height){
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

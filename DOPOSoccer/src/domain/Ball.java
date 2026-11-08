package domain;

public class Ball extends Entity{
    private Power chargedPower;
    private Player chargedBy;

    private int diameter;

    private double xSpeed;
    private double ySpeed;

    public Ball(int xPositon, int yPositon, int diameter) throws DopoSoccerException{
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.diameter = diameter;
    }

    public void translate() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public int getDiameter(){
         return diameter; 
    }

    public Power getChargedPower(){
        return chargedPower;
    }

    public Player getChargedBy(){
        return chargedBy;
    }
    
}

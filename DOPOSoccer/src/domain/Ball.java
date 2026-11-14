package domain;

public class Ball extends Entity{
    private Power chargedPower;
    private Player chargedBy;

    private int diameter;

    private double xSpeed;
    private double ySpeed;

    public Ball(double xPositon, double yPositon) throws DopoSoccerException{
        super(xPositon, yPositon);
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

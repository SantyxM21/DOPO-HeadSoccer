package domain;

public class Ilusion extends Power {
    
    public Ilusion(){
        super("Ilusion", 30, 2.5);
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

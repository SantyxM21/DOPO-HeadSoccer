package domain;

public class Tornado extends Power{
    
    public Tornado(){
        super("Tornado", 40, new UnconsiousEffect());
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

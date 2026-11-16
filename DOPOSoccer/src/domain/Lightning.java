package domain;

public class Lightning extends Power {
    
    public Lightning(){
        super("Rayo", 60, 3.0, new ElectroEffect());
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

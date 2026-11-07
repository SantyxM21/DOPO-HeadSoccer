package domain;

public class Lightning extends Power {
    
    public Lightning(){
        super("Rayo", 60, 3);
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

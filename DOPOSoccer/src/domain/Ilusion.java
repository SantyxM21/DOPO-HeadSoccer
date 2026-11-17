package domain;

public class Ilusion extends Power {
    
    public Ilusion(){
        super("Ilusión", 30, new ConfusionEffect());
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

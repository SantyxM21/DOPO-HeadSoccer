package domain;

public class Ilusion extends Power {
    
    public Ilusion(){
        super("Ilusión", 30, 2.5, new ConfusionEffect());
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

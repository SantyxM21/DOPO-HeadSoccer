package domain;

public class BlueAura extends Power {
    
    public BlueAura(){
        super("Aura Azul", 40, new PushEffect());
    }

    @Override 
    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }
}

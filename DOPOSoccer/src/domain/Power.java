package domain;

public abstract class Power {
    protected Effect effect;

    protected String name;
    protected double shootDamage; 

    protected Power(String name, double shootDamage, Effect effect){
        this.name = name;
        this.shootDamage = shootDamage;
        this.effect = effect;
    }

    public void applyPower() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public String getName(){
        return name;
    }

    public double getShootDamage(){
        return shootDamage;
    }

    public double getDurationEffect(){
        return effect.getDuration();
    }

    public Effect getEffect(){
        return effect;
    }
}
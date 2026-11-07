package domain;

public abstract class Power {
    protected String name;
    protected double shootDamage; 
    protected double durationEfect;

    protected Power(String name, double shootDamage, double durationEfect){
        this.name = name;
        this.shootDamage = shootDamage;
        this.durationEfect = durationEfect;
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
        return durationEfect;
    }
}
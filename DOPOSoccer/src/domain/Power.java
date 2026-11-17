package domain;

public abstract class Power {
    public static final double SHOT_SPEED = 14;

    protected String name;
    protected double shootDamage;

    protected Power(String name, double shootDamage){
        this.name = name;
        this.shootDamage = shootDamage;
    }

    protected abstract Effect createEffect();

    //Daña a la victima y devuelve un efecto para aplicarle
    public Effect hit(Player victim, Player shooter){
        victim.takeDamage(shootDamage * shooter.getKickMultiplier());
        return createEffect();
    }

    //Velocidad con la que sale el tiro
    public Vector2D shotVelocity(int direction, Field field){
        return new Vector2D(direction * field.scale(SHOT_SPEED), 0);
    }

    public String getName(){
        return name;
    }

    public double getShootDamage(){
        return shootDamage;
    }

    public double getDurationEffect(){
        return createEffect().getDuration();
    }
}

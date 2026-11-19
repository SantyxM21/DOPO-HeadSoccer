package domain;

public abstract class Power {
    public static final double SHOT_SPEED = 5;

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

    
    public String getName(){
        return name;
    }
    
    public double getShootDamage(){
        return shootDamage;
    }
    
    public double getDurationEffect(){
        return createEffect().getDuration();
    }

    // Velocidad con la que sale el tiro
    public Vector2D shotVelocity(int direction, Field field){
        return new Vector2D(direction * field.scale(SHOT_SPEED), 0);
    }
    
    // Gravedad que afecta al balon mientras lleva el poder
    // por defecto es 0, el tiro sale recto
    public double shotGravity(Field field){
        return 0;
    }

    // Velocidad vertical con la que el balon con poder rebota al tocar el suelo
    // por defecto rebota como un balon normal, perdiendo fuerza en cada bote
    public double floorBounce(double ySpeed, Field field){
        return -ySpeed * Ball.FLOOR_FRICTION;
    }

    // Posición desde la que sale el balon con poder
    // todos los poderes salen por defecto a la misma altura que el personaje
    public Vector2D positionAnimation(Player player, int direction, Field field){
        Vector2D centerPlayer = player.center();
        int radiusPlayer = player.diameter/2;

        double initialX = centerPlayer.getX() + (radiusPlayer*direction);
        double initialY = centerPlayer.getY();

        return new Vector2D(initialX, initialY);
    }
}

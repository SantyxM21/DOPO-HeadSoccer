package domain;

public class Tornado extends Power{
    public static final double ROLL_SPEED = 8;        // velocidad horizontal del remolino
    public static final double BOUNCE_SPEED = 6;    // velocidad con la que sube en cada bote
    public static final double BOUNCE_GRAVITY = 0.6;  // gravedad alta pare rebotes bajos y rapidos 

    public Tornado(){
        super("Tornado", 40);
    }

    @Override
    protected Effect createEffect(){
        return new UnconsiousEffect();
    }

    @Override 
    public Vector2D positionAnimation(Player player, int direction, Field field){
        int ground = field.getGroundY();
        int playerRadius = player.getDiameter()/2;
        double playerX = player.center().getX();

        return  new Vector2D(playerX + (direction*playerRadius), ground);
    }

    @Override
    public Vector2D shotVelocity(int direction, Field field){
        return new Vector2D(direction * field.scale(ROLL_SPEED), -field.scale(BOUNCE_SPEED));
    }

    @Override
    public double shotGravity(Field field){
        return field.scale(BOUNCE_GRAVITY);
    }

    @Override
    public double floorBounce(double ySpeed, Field field){
        // siempre rebota con la misma fuerza (no pierde energia por la friccion del suele, para esta poder)
        return -field.scale(BOUNCE_SPEED);
    }
}

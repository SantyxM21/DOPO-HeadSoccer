package domain;

public class Tornado extends Power{

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
}

package domain;

public class OfensiveMachine extends Machine{
    public OfensiveMachine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile, machineType);
    }

    @Override
    public void act(Match match, double seconds){
        Ball ball = match.getBall();
        Vector2D ballCenter = ball.center();
        double ballRadius = ball.radius();
        double xBall = ballCenter.getX();
        double yBall = ballCenter.getY();

        double mchX = center().getX();
        double mchY = center().getY();
        double mchRadius = radius();

        if (xBall  < mchX-ballRadius*1.6){
            move(-1);
        }
        else if (xBall > mchX-ballRadius*0.9){
            move(1);
        }

        if (yBall < mchY-ballRadius && yBall > mchY-mchRadius*2.1 && xBall < mchX+mchRadius && xBall > mchX-mchRadius*1.1){
            jump();
        }

        if (!hasAura()){
            powerActivation();
        }
    }
}

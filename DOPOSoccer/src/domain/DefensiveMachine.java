package domain;

public class DefensiveMachine extends Machine{
    private int fielWidth;

    public DefensiveMachine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile, machineType);
        fielWidth = field.getWidth();
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
        
        if (xBall > mchX-mchRadius-ballRadius){
            move(1);
        }
        else if (xBall  < mchX-ballRadius*1.6 && mchX > fielWidth*0.80 ){
            move(-1);
        }
        
        if (yBall < mchY-ballRadius && yBall > mchY-mchRadius*2.8 && xBall < mchX+mchRadius*1.5 && xBall > mchX-mchRadius*1.8){
            jump();
        }

        if(yBall < mchY+mchRadius && yBall > mchY-mchRadius && xBall < mchX-mchRadius && xBall > mchX-mchRadius-ballRadius*1.1 && !hasAura()){
            match.kick(this);
        }
        if (!hasAura()){
            powerActivation();
        }


        
        




    }
}

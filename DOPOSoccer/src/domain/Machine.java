package domain;

public class Machine extends Player {

    // TODO: los controles del segundo jugar aun funcionan cuando la maquina controla
    public static final int DUMB = 1;
    public static final int OFENSIVE = 2;
    public static final int DEFENSIVE = 3;
    public static final int EXPERT = 4;
    public static final String[] PROFILE_NAMES = {"Torpe", "Ofensiva", "Defensiva", "Experta"};   

    public static final int POSSIBLE_MOVES = 5;
    
    public Machine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile);
    }

    protected void attack(Match match){
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

    protected void defend(Match match){
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
        else if (xBall  < mchX-ballRadius*1.6 && mchX > field.getWidth()*0.80 ){
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

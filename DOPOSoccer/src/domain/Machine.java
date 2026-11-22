package domain;

public abstract class Machine extends Player {

    public static final int DUMB = 1;
    public static final int OFENSIVE = 2;
    public static final int DEFENSIVE = 3;
    public static final int EXPERT = 4;
    public static final String[] PROFILE_NAMES = {"Torpe", "Ofensiva", "Defensiva", "Experta"};   

    public static final int POSSIBLE_MOVES = 5;
    
    protected Machine(Character_ ch, double x, double y, Field field, Profile profile) throws DopoSoccerException{
        super(ch, x, y, field, profile);
    }

    @Override
    public abstract void act(Match match, double seconds);

    // La maquina decide sola en act, asi que ignora las teclas del jugador de su lado
    @Override
    public boolean isKeyboardControlled(){
        return false;
    }

    /*
    This method tells how far in front of the machine the ball is
    @param match the match
    @return positive if the ball is between the machine and the rival goal, negative if it is behind
    */
    protected double ballAhead(Match match){
        return (match.getBall().center().getX() - center().getX()) * match.directionOf(this);
    }

    /*
    This method tells how far the machine is from the wall behind its own goal (porteria)
    @param match the match
    @return the horizontal distance to its own wall
    */
    protected double distanceToOwnWall(Match match){
        double x = center().getX();
        if (match.directionOf(this) > 0) return x;      // ataca a la derecha, su arco esta a la izquierda
        return field.getWidth() - x;
    }

    protected void attack(Match match){
        Ball ball = match.getBall();
        double ballRadius = ball.radius();
        double yBall = ball.center().getY();
        double ahead = ballAhead(match);
        int forward = match.directionOf(this);

        double mchY = center().getY();
        double mchRadius = radius();

        if (ahead > ballRadius*1.6){                    // el balon esta lejos adelante, va por el
            move(forward);
        }
        else if (ahead < ballRadius*0.9){               // el balon esta muy cerca o detras, se acomoda detras de el
            move(-forward);
        }

        if (yBall < mchY-ballRadius && yBall > mchY-mchRadius*2.1 && ahead > -mchRadius && ahead < mchRadius*1.1){
            jump();
        }

        if (!hasAura()){
            powerActivation();
        }
    }

    protected void defend(Match match){
        Ball ball = match.getBall();
        double ballRadius = ball.radius();
        double yBall = ball.center().getY();
        double ahead = ballAhead(match);
        int forward = match.directionOf(this);

        double mchY = center().getY();
        double mchRadius = radius();

        if (ahead < mchRadius+ballRadius){                                                  // el balon lo paso, vuelve a su arco
            move(-forward);
        }
        else if (ahead > ballRadius*1.6 && distanceToOwnWall(match) < field.getWidth()*0.20){   // sale un poco del arco, sin alejarse mucho
            move(forward);
        }

        if (yBall < mchY-ballRadius && yBall > mchY-mchRadius*2.8 && ahead > -mchRadius*1.5 && ahead < mchRadius*1.8){
            jump();
        }

        if(yBall < mchY+mchRadius && yBall > mchY-mchRadius && ahead > mchRadius && ahead < mchRadius+ballRadius*1.1 && !hasAura()){
            match.kick(this);
        }
        if (!hasAura()){
            powerActivation();
        }
    }
}

package domain;

public class Ball extends CircularEntity{
    public static final int BASE_DIAMETER = 40;
    public static final double WALL_FRICTION = 0.8;
    public static final double AIR_FRICTION = 0.999;
    public static final double FLOOR_FRICTION = 0.75;
    public static final double MIN_ROLLING_SPEED = 0.7;

    private Power chargedPower;
    private Player chargedBy;

    private final double minRollingSpeed;

    public Ball(Field field){
        super(0, 0, field, BASE_DIAMETER);
        this.minRollingSpeed = field.scale(MIN_ROLLING_SPEED);
        kickOff();
    }

    public void kickOff(){
        clearCharge();
        xPosition = field.getWidth() / 2 - diameter / 2;
        yPosition = field.getHeight() / 3;
        velocity = new Vector2D(0, 0); //Valores Temporales
    }

    public void move(){
        double previousBottomBallPosition = yPosition + diameter;

        xPosition += velocity.getX();
        yPosition += velocity.getY();

        int fieldWidth = field.getWidth();
        int fieldGroundY = field.getGroundY();

        if(xPosition + diameter > fieldWidth){
            xPosition = fieldWidth - diameter;
            velocity.setX(velocity.getX() * -WALL_FRICTION);
            clearCharge(); 
        }else if(xPosition < 0){
            xPosition = 0;
            velocity.setX(velocity.getX() * -WALL_FRICTION);
            clearCharge(); 
        }else{
            velocity.setX(velocity.getX() * AIR_FRICTION);
        }


        if (yPosition + diameter > fieldGroundY){
            yPosition = fieldGroundY - diameter;
            if (chargedPower != null) velocity.setY(chargedPower.floorBounce(velocity.getY(), field));   // cada poder decide como rebota
            else if (Math.abs(velocity.getY()) < 1) velocity.setY(0);
            else velocity.setY(velocity.getY() * -FLOOR_FRICTION);
        } else if (yPosition < 0) {
            yPosition = 0;
            velocity.setY(-velocity.getY());
        } else if(chargedPower == null){
            velocity.setY(velocity.getY() + gravity);
        } else {
            velocity.setY(velocity.getY() + chargedPower.shotGravity(field));   // por defecto 0, el tiro sale recto
        }
        onCrossbarBouncing(previousBottomBallPosition);

        if (yPosition + diameter >= fieldGroundY && Math.abs(velocity.getX()) < minRollingSpeed) velocity.setX(0);
    }

    private void onCrossbarBouncing(double previousBottomBallPosition){
        double bottomBallPosition = yPosition + diameter;
        for (int i = Match.LEFT_PLAYER; i <= Match.RIGHT_PLAYER; i++){
            GoalFrame goal = field.getGoalFrame(i);
            double top = goal.getYPosition(); //parte alta del crossbar

            boolean xNextToGoalFrame = xPosition + diameter > goal.getXPosition() && xPosition < goal.getXPosition() + goal.getWidth();
            boolean underCrossbar = previousBottomBallPosition <= top && bottomBallPosition > top; //Si en el frame esta cruzando el crosasbar

            if (xNextToGoalFrame && underCrossbar){ //si debe rebotar con el crossbar
                yPosition = top - diameter;
                onCrossbarVertical();
            }
        }
    }

    private void onCrossbarVertical(){
        if (velocity.getY() < 1 && velocity.getY() > -1) velocity.setY(0);//un golpe muy suave
        else velocity.setY(velocity.getY() * -FLOOR_FRICTION); //un golpe muy fuerte rebota
    }

    public void translate(double dx, double dy){
        xPosition += dx;
        yPosition += dy;
    }

    public void keepInsideField(){
        xPosition = Math.max(0, Math.min(xPosition, field.getWidth() - diameter));
        yPosition = Math.max(0, Math.min(yPosition, field.getGroundY() - diameter));
    }

    public Power getChargedPower(){
        return chargedPower;
    }

    public Player getChargedBy(){
        return chargedBy;
    }

    public void chargePower(Power power, Player chargedBy, int direction){
        chargedPower = power;
        this.chargedBy = chargedBy;

        setPositions(power.positionAnimation(chargedBy, direction, field));        // posición desde donde sale la animación del poder
        setVelocity(power.shotVelocity(direction, field));                  // la velocidad con la que sale el balon al estar cargada de un poder
    }

    public void clearCharge(){
        chargedPower = null;
        chargedBy = null;
    }
    
    public boolean hasPower(){
        if(chargedPower == null) return false;
        return true;
    }
}

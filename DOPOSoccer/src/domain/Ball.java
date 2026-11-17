package domain;

public class Ball extends Entity{
    public static final int BASE_DIAMETER = 40;
    public static final double GRAVITY = 0.15; 
    public static final double WALL_FRICTION = 0.8;
    public static final double AIR_FRICTION = 1;
    public static final double FLOOR_FRICTION = 0.70;

    public static final double POWER_SPEED = 14;

    private Power chargedPower;
    private Player chargedBy;

    private int diameter;
    private final Field field;
    private final double gravity;
    private Vector2D velocity;

    public Ball(Field field){
        super(0, 0); //Valores temporales
        this.field = field;
        this.diameter = (int) field.scale(BASE_DIAMETER);
        this.gravity = field.scale(GRAVITY);
        kickOff();
    }

    public void kickOff(){
        clearCharge();
        xPosition = field.getWidth() / 2 - diameter / 2;
        yPosition = field.getHeight() / 4;
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

            if (Math.abs(velocity.getX()) < 1.9) velocity.setX(0);
        }


        if (yPosition + diameter > fieldGroundY){
            yPosition = fieldGroundY - diameter;  
                if (Math.abs(velocity.getY()) < 1) velocity.setY(0); 
                else velocity.setY(velocity.getY() * -FLOOR_FRICTION);
        } else if (yPosition < 0) {
            yPosition = 0;  
            velocity.setY(-velocity.getY());                                
        } else if(chargedPower == null){
            velocity.setY(velocity.getY() + gravity);
        }
        onCrossbarBouncing(previousBottomBallPosition); 
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

    public Vector2D center(){
        return new Vector2D(xPosition + diameter / 2.0, yPosition + diameter / 2.0);
    }

    public Vector2D getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2D v){
        this.velocity = v;
    }

    public int getDiameter(){
         return diameter; 
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
        velocity = new Vector2D(direction * field.scale(POWER_SPEED), 0);
    }

    public void clearCharge(){
        chargedPower = null;
        chargedBy = null;
    }
    
}

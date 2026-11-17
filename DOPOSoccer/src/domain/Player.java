package domain;

public abstract class Player extends CircularEntity {
    public static final int PLAYER_SIZE = 80;
    public static final double SPEED = 5.0;
    public static final double JUMP_FORCE = -5.5;  // negativo ya que Y disminuye hacia arriba

    //Valores de prueba
    public static final double KICK_RANGE = 30;
    public static final double X_KICK_SPEED = 8;
    public static final double Y_KICK_SPEED = 8;

    public static final double MAX_POWER_BAR = 100.0;
    public static final double POWER_BAR_CHARGE_SECONDS = 1.0;

    private final int groundY;
    private double speed;
    private double jumpForce;
    private double kickForce;

    private final double kickRange;
    private final double xKickSpeed;
    private final double yKickSpeed;

    protected Character_ selectedCharacter;
    protected Profile profile;

    protected int goals;
    protected double health;
    protected double powerBar;

    protected boolean aura;


    public Player(Character_ character, double x, double y, Field field, Profile profile) throws DopoSoccerException{
        super(x, y, field, PLAYER_SIZE);
        if (character == null || profile == null) throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
        this.selectedCharacter = character;
        this.profile = profile;

        this.groundY = field.getGroundY() - diameter;
        this.kickRange = field.scale(KICK_RANGE);
        this.xKickSpeed = field.scale(X_KICK_SPEED);
        this.yKickSpeed = field.scale(Y_KICK_SPEED);

        restoreValues();
        resetForNewMatch();
    }

    public void move(int direction){
        xPosition += direction * speed;
        velocity.setX(direction * speed);

        //Verificaciones para que no se salga de la pantalla
        if (xPosition < 0 ) xPosition = 0;
        if(xPosition > field.getWidth() - diameter) xPosition = field.getWidth() - diameter;
    }

    public void translate(double dx, double dy){
        xPosition = Math.max(0, Math.min(xPosition + dx, field.getWidth() - diameter));
        yPosition = Math.min(yPosition + dy, groundY);
    }

    public void kick(Ball ball, int direction){
        if (kickForce == 0) return;
        Vector2D distance = Vector2D.subtract(ball.center(), center());

        boolean isClose = Vector2D.length(distance) <= radius() + ball.radius() + kickRange; //Si esta cerca al balon, tomando su centro como referencia
        boolean isInFront = direction * distance.getX() >= 0; //Si esta hacia donde patea el jugador

        if (isClose && isInFront){
            if (aura) powerShoot(ball, direction);
            else{
                ball.clearCharge(); // evita que quede un balón cargado "flotando" si el rival lo patea
                ball.setVelocity(new Vector2D(direction * xKickSpeed * kickForce, -yKickSpeed * kickForce));
            }
        }
    }

    public void jump(){
        if (yPosition == groundY){     // Condición para evitar que salte en el aire
            velocity.setY(jumpForce);
        }
    }

    public void applyGravity(){
        yPosition += velocity.getY();
        velocity.setY(velocity.getY() + gravity);

        if (yPosition >= groundY){   // una vez que llega al suele ya no baja mas
            yPosition = groundY;
            velocity.setY(0);
        }
    }

    public double getSpeedMultiplier(){
        int speedStat = profile.getStatLevel(Stats.SPEED_STAT);
        return 1.0 + 0.03 * (speedStat - 1);
    }

    public double getJumpMultiplier(){
        int jumpStat = profile.getStatLevel(Stats.JUMP_STAT);
        return 1.0 + 0.03 * (jumpStat - 1);
    }

    public double getKickMultiplier(){
        int kickStat = profile.getStatLevel(Stats.KICK_STAT);
        return 1.0 + 0.10 * (kickStat - 1);
    }

    public double getMaxHealth(){
        int healthStat = profile.getStatLevel(Stats.HEALTH_STAT);
        return 100.0 * (1.0 + 0.10 * (healthStat - 1));
    } 

    public Character_ getSelectedCharacter(){
        return selectedCharacter;
    }

    public int getGoals(){
        return goals;
    }

    public double getHealth(){
        return health;
    }

    public double getPowerBar(){
        return powerBar;
    }

    public Profile getProfile(){
        return profile;
    }

    //Similar al tac()
    public void endFrame(){
        velocity.setX(0);
    }

    public void scoredGoal(){
        goals++;
    }

    public void powerActivation(){
        if (powerBar >= MAX_POWER_BAR && !aura && selectedCharacter.getPower() != null) {
            aura = true;
            powerBar = 0.0;
        }
    }

    public void restoreValues(){
        speed = field.scale(SPEED) * getSpeedMultiplier();
        jumpForce = field.scale(JUMP_FORCE) * Math.sqrt(getJumpMultiplier()); // la altura crece con el cuadrado de la fuerza
        kickForce = 1.0;
    }

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getJumpForce(){
        return jumpForce;
    }

    public void setJumpForce(double jumpForce){
        this.jumpForce = jumpForce;
    }

    public double getKickForce(){
        return kickForce;
    }

    public void setKickForce(double kickForce){
        this.kickForce = kickForce;
    }

    public void resetForNewMatch(){
        this.health = getMaxHealth();
        this.powerBar = 0.0;
        this.goals = 0;
    }

    //Llamada cada frame
    public void update(double seconds){
        powerBar = Math.min(MAX_POWER_BAR, powerBar + MAX_POWER_BAR / POWER_BAR_CHARGE_SECONDS * seconds); //rapidez de carga
    }

    //Hacer el tiro especial
    public void powerShoot(Ball ball, int direction){
        ball.chargePower(selectedCharacter.getPower(), this, direction);
        aura = false; 
    }

    public void powerBarReceivedGoal(){
        powerBar = Math.min(MAX_POWER_BAR, powerBar + MAX_POWER_BAR / 4);  //+1/4
    }

    public void takeDamage(double damage){
        health = Math.max(0, health - damage);
    }

    public void pushHorizontal(double DistancePushedPerFrame){
        double scaledDistancePushed = field.scale(DistancePushedPerFrame);
        double rightmostAllowedX = field.getWidth() - diameter;
        xPosition = Math.max(0, Math.min(xPosition + scaledDistancePushed, rightmostAllowedX));
        velocity.setX(scaledDistancePushed);
    }

    public boolean hasAura(){
        return aura;
    }
}

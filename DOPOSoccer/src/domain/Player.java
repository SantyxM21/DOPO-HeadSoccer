package domain;

public abstract class Player extends CircularEntity{
    public static final int PLAYER_SIZE = 80;
    public static final double SPEED = 3.5;
    public static final double JUMP_FORCE = -4.8;  // negativo ya que Y disminuye hacia arriba

    //Valores de prueba
    public static final double KICK_RANGE = 30;
    public static final double X_KICK_SPEED = 8;
    public static final double Y_KICK_SPEED = 8;
    public static final double KICK_DAMAGE = 10;     

    public static final double MAX_POWER_BAR = 100.0;
    public static final double POWER_BAR_CHARGE_SECONDS = 10.0;

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

    private Effect effect;          // efecto que tiene encima en este momento (null si ninguno)


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

    public void kick(Ball ball, Player rival, int direction){
        if (kickForce == 0) return;

        if (inKickRange(ball, direction)){                              // si la patada toca el balon, solo impulsa el balon
            ball.clearCharge(); // evita que quede un balón cargado "flotando" si el rival lo patea
            ball.setVelocity(new Vector2D(direction * xKickSpeed * kickForce, -yKickSpeed * kickForce));
        }
        else if (inKickRange(rival, direction) && rival.getHealth() > 0){   // a un rival aturdido no se le hace mas daño
            rival.takeDamage(KICK_DAMAGE * getKickMultiplier());
        }
    }

    /*
    This method tells if the player can kick the ball right now
    @param ball the ball
    @param direction the direction the player kicks to (1 right, -1 left)
    @return true if the player can kick and the ball is within reach of its foot
    */
    public boolean reachesBall(Ball ball, int direction){
        return kickForce != 0 && inKickRange(ball, direction);
    }

    /*
    This method tells if a circular entity is within reach of the player's foot
    @param target the ball or the rival
    @param direction the direction the player kicks to (1 right, -1 left)
    @return true if the target is close and in front of the player
    */
    private boolean inKickRange(CircularEntity target, int direction){
        Vector2D distance = Vector2D.subtract(target.center(), center());
        boolean isClose = Vector2D.length(distance) <= radius() + target.radius() + kickRange; //Si esta cerca, tomando su centro como referencia
        boolean isInFront = direction * distance.getX() >= 0; //Si esta hacia donde patea el jugador
        return isClose && isInFront;
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
        return 1.0 + 0.10 * (speedStat - 1);
    }

    public double getJumpMultiplier(){
        int jumpStat = profile.getStatLevel(Stats.JUMP_STAT);
        return 1.0 + 0.10 * (jumpStat - 1);
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

    public double getJumpForce(){
        return jumpForce;
    }

    public double getKickForce(){
        return kickForce;
    }

    // ==================== ESTADOS QUE CAUSAN LOS EFECTOS ====================
    // Los efectos cambian al jugador solo con estos metodos, restoreValues() lo devuelve a la normalidad

    /* This method leaves the player unable to move, jump or kick */
    public void immobilize(){
        speed = 0;
        jumpForce = 0;
        kickForce = 0;
    }

    /*
    This method makes the player move slower
    @param factor the part of its current speed it keeps (0.5 is half)
    */
    public void slowDown(double factor){
        speed *= factor;
    }

    /* This method leaves the player unable to jump */
    public void blockJump(){
        jumpForce = 0;
    }

    public void resetForNewMatch(){
        this.health = getMaxHealth();
        this.powerBar = 0.0;
        this.goals = 0;
    }

    public void act(Match match, double seconds){
    }

    public boolean isKeyboardControlled(){
        return true;
    }

    //Llamada cada frame
    public void update(double seconds){
        powerBar = Math.min(MAX_POWER_BAR, powerBar + MAX_POWER_BAR / POWER_BAR_CHARGE_SECONDS * seconds); //rapidez de carga
        updateEffect(seconds);
    }

    // ==================== EFECTOS ====================

    /*
    This method puts an effect on the player, replacing the one it had
    @param newEffect the effect to apply
    @param direction the direction of the push (1 right, -1 left, 0 none)
    */
    public void applyEffect(Effect newEffect, int direction){
        removeEffect();                                 // el nuevo efecto reemplaza al anterior
        effect = newEffect;
        effect.applyEffect(this, direction, field);
    }

    /* This method advances the current effect and removes it when its time is over */
    private void updateEffect(double seconds){
        if (effect == null) return;
        effect.update(this, seconds);
        if (effect.isFinished()) removeEffect();
    }

    /* This method ends the current effect, if any, returning the player to normal */
    public void removeEffect(){
        if (effect == null) return;
        Effect ended = effect;
        effect = null;
        ended.end(this);                                // cada efecto sabe como devolver al jugador a la normalidad
    }

    public boolean hasEffect(){
        return effect != null;
    }

    public Effect getEffect(){
        return effect;
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

    public void restoreHealth(){
        health = getMaxHealth();
    }


    public boolean hasAura(){
        return aura;
    }
}

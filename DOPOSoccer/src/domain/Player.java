package domain;

public abstract class Player extends Entity {
    public static final int PLAYER_SIZE = 80;
    public static final double SPEED = 5.0;
    public static final double JUMP_FORCE = -6.5;  // negativo ya que Y disminuye hacia arriba
    public static final double GRAVITY = 0.15;     
    public final int GROUND_Y;

    //Valores de prueba
    public static final double KICK_RANGE = 30;
    public static final double X_KICK_SPEED = 8;
    public static final double Y_KICK_SPEED = 8;

    public static final double MAX_POWER_BAR = 100.0;
    public static final double POWER_BAR_CHARGE_SECONDS = 10.0;

    protected final Field field;
    private final int size;
    private final double speed;
    private final double jumpForce;
    private final double gravity;

    private final double kickRange;
    private final double xKickSpeed;
    private final double yKickSpeed;
    private double xVelocity; //Movimiento en el frame actual 
    private double yVelocity = 0;

    protected Character_ selectedCharacter;
    protected Profile profile;
    protected Effect effect;

    protected int goals;
    protected double survival;
    protected double powerBar;

    protected boolean aura;


    public Player(String character, double x, double y, Field field, Profile profile) throws DopoSoccerException{
        super(x, y);
        if (profile == null) throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
        GROUND_Y = (int) y;
        this.selectedCharacter = createCharacter(character);
        this.profile = profile;

        this.field = field;
        this.size = (int) field.scale(PLAYER_SIZE);
        this.speed = field.scale(SPEED);
        this.jumpForce = field.scale(JUMP_FORCE);
        this.gravity = field.scale(GRAVITY);

        this.kickRange = field.scale(KICK_RANGE);
        this.xKickSpeed = field.scale(X_KICK_SPEED);
        this.yKickSpeed = field.scale(Y_KICK_SPEED);

        startStats();               
    }

    public void move(int direction){
        if (isBlocked()) return;
        xPosition += direction * speed;
        xVelocity = direction * speed;

        //Verificaciones para que no se salga de la pantalla
        if (xPosition < 0 ) xPosition = 0;
        if(xPosition > field.getWidth() - size) xPosition = field.getWidth() - size;
    }

    public void kick(Ball ball, int direction){
        if (isBlocked()) return;
        Vector2D distance = Vector2D.subtract(ball.center(), center());

        boolean isClose = Vector2D.length(distance) <= (size + ball.getDiameter()) / 2.0 + kickRange; //Si esta cerca al balon, tomando su centro como referencia
        boolean isInFront = direction * distance.getX() >= 0; //Si esta hacia donde patea el jugador

        if (isClose && isInFront){
            if (aura) powerShoot(ball, direction);
            else{
                ball.clearCharge(); // evita que quede un balón cargado "flotando" si el rival lo patea
                ball.setVelocity(new Vector2D(direction * xKickSpeed, -yKickSpeed));
            }
        }
    }

    public void jump(){
        if (isBlocked()) return;
        if (yPosition == GROUND_Y){     // Condición para evitar que salte en el aire
            yVelocity = jumpForce;
        }
    }

    public void applyGravity(){
        yPosition += yVelocity;
        yVelocity += gravity;

        if (yPosition >= GROUND_Y){   // una vez que llega al suele ya no baja mas
            yPosition = GROUND_Y;
            yVelocity = 0;
        }
    }

    public double getKickMultiplier(){
        int kickStat = profile.getStatLevel("kick");
        return 1.0 + 0.10 * (kickStat - 1);
    }

    public double getMaxSurvival(){
        int survival = profile.getStatLevel("survival");
        return 100.0 * (1.0 + 0.10 * (survival - 1));
    } 

    public Character_ getSelectedCharacter(){
        return selectedCharacter;
    }

    public int getGoals(){
        return goals;
    }

    public double getSurvival(){
        return survival;
    }

    public double getPowerBar(){
        return powerBar;
    }

    protected  Character_ createCharacter(String country) throws DopoSoccerException{
        switch (country) {
            case "Corea del Sur":
                return new Corea();
            case "Camerún":
                return new Camerun();
            case "Nigeria":
                return new Nigeria();
            case "Estados Unidos":
                return new USA();
            default:
                throw new DopoSoccerException(DopoSoccerException.INEXISTENT_CHARACTER);
        }
    }

    public Profile getProfile(){
        return profile;
    }

    public int getSize(){
        return size;
    }

    public Vector2D center(){
        return new Vector2D(xPosition + size / 2.0, yPosition + size / 2.0);
    }

    public Vector2D getVelocity(){
        return new Vector2D(xVelocity, yVelocity);
    }

    //Similar al tac()
    public void endFrame(){
        xVelocity = 0;
    }

    public void scoredGoal(){
        goals++;
    }

    public void kickOffPosition(double x, double y){
        xPosition = x;
        yPosition = y;

        xVelocity = 0;
        yVelocity = 0;
    }

    public void powerActivation(){
        if (powerBar >= MAX_POWER_BAR && !aura && selectedCharacter.getPower() != null) {
            aura = true;
            powerBar = 0.0;
        }
    }

    public void startStats(){
        this.survival = getMaxSurvival();
        this.powerBar = 0.0;
        this.goals = 0;
    }

    //Llamada cada frame
    public void update(double seconds){
        if (effect != null){
            effect.tick(this);
            effect.update(seconds);
            if (effect.isFinished()) effect = null;
        }
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
        survival = Math.max(0, survival - damage);
    }

    public void receiveEffect(Effect newEffect, int direction){
        effect = newEffect;
        newEffect.applyEffect(this, direction);
    }

    public void pushHorizontal(double DistancePushedPerFrame){
        double scaledDistancePushed = field.scale(DistancePushedPerFrame);
        double rightmostAllowedX = field.getWidth() - size;
        xPosition = Math.max(0, Math.min(xPosition + scaledDistancePushed, rightmostAllowedX));
        xVelocity = scaledDistancePushed;
    }

    private boolean isBlocked(){
        return effect!= null && effect.blockPlayer();
    }

    public boolean hasAura(){
        return aura;
    }

    public boolean hasEffect(){
        return effect != null;
    }
}

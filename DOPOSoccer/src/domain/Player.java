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
    protected double health;
    protected double powerBar;


    public Player(String character, double x, double y, Field field) throws DopoSoccerException{
        super(x, y);
        GROUND_Y = (int) y;
        this.selectedCharacter = createCharacter(character);

        this.field = field;
        this.size = (int) field.scale(PLAYER_SIZE);
        this.speed = field.scale(SPEED);
        this.jumpForce = field.scale(JUMP_FORCE);
        this.gravity = field.scale(GRAVITY);

        this.kickRange = field.scale(KICK_RANGE);
        this.xKickSpeed = field.scale(X_KICK_SPEED);
        this.yKickSpeed = field.scale(Y_KICK_SPEED);
    }

    public void move(int direction) {
        xPosition += direction * speed;
        xVelocity = direction * speed;

        //Verificaciones para que no se salga de la pantalla
        if (xPosition < 0 ) xPosition = 0;
        if(xPosition > field.getWidth() - size) xPosition = field.getWidth() - size;
    }

    public void kick(Ball ball, int direction){
        Vector2D distance = Vector2D.subtract(ball.center(), center());

        boolean isClose = Vector2D.length(distance) <= (size + ball.getDiameter()) / 2.0 + kickRange; //Si esta cerca al balon, tomando su centro como referencia
        boolean isInFront = direction * distance.getX() >= 0; //Si esta hacia donde patea el jugador

        if (isClose && isInFront) ball.setVelocity(new Vector2D(direction * xKickSpeed, -yKickSpeed));
    }

    public void jump() {
        if (yPosition == GROUND_Y){     // Condición para evitar que salte en el aire
            yVelocity = jumpForce;
        }
    }

    public void applyGravity() {
        yPosition += yVelocity;
        yVelocity += gravity;

        if (yPosition >= GROUND_Y){   // una vez que llega al suele ya no baja mas
            yPosition = GROUND_Y;
            yVelocity = 0;
        }
    }

    public double getKickMultiplier(){
        return 0.0;
        //return 1 + 0.10 * (kickStat - 1);
    }

    public double getMaxHp(){
        return 0.0;
        //return  100 * (1 + 0.10 * (survivalStat - 1));
    } 

    public Character_ getSelectedCharacter(){
        return selectedCharacter;
    }

    public int getGoals(){
        return goals;
    }

    public double getHp(){
        return health;
    }

    public double getPowerBar(){
        return powerBar;
    }

    protected  Character_ createCharacter (String country) throws DopoSoccerException{
        switch (country) {
            case "Corea":
                return new Corea();
            case "Camerun":
                return new Camerun();
            case "Nigeria":
                return new Nigeria();
            case "USA":
                return new USA();
            default:
                throw new DopoSoccerException(DopoSoccerException.INEXISTENT_CHARACTER);
        }
    }

    public void setProfile(Profile profile){
        this.profile = profile;
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


}

package domain;

public abstract class Player extends Entity {
    public static final int PLAYER_SIZE = 80;
    public static final double SPEED = 5.0;
    public static final double JUMP_FORCE = -6.5;  // negativo ya que Y disminuye hacia arriba
    public static final double GRAVITY = 0.15;     
    public final int GROUND_Y;

    protected final Field field;
    private final int size;
    private final double speed;
    private final double jumpForce;
    private final double gravity;

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
    }

    public void move(int direction) {
        xPosition += direction * speed;
        //Verificaciones para que no se salga de la pantalla
        if (xPosition < 0 ) xPosition = 0;
        if(xPosition > field.getWidth() - size) xPosition = field.getWidth() - size;
    }

    public void kick() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
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
}

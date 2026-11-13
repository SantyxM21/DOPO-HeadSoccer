package domain;

public abstract class Player extends Entity {
    public static final int PLAYER_SIZE = 80;
    public static final double SPEED = 5.0;
    public static final double JUMP_FORCE = -6.5;  // negativo ya que Y disminuye hacia arriba
    public static final double GRAVITY = 0.15;     
    public final int GROUND_Y;

    private double yVelocity = 0;

    protected Character_ selectedCharacter;
    protected Profile profile;
    protected Effect effect;

    protected int goals;
    protected double health;
    protected double powerBar;


    public Player(String character, double x, double y) {
        super(x, y);
        GROUND_Y = (int) y;
        setCharacter_(character);
    }

    private void setCharacter_(String character){
        if(character.equals("SouthKorea")) selectedCharacter = new SouthKorea();
    }

    public void move(int direction) {
        xPosition += direction * SPEED;
    }

    public void kick() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void jump() {
        if (yPosition == GROUND_Y){     // Condición para evitar que salte en el aire
            yVelocity = JUMP_FORCE;
        }
    }

    public void applyGravity() {
        yPosition += yVelocity;
        yVelocity += GRAVITY;

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
/* 
    public Power getPower(){
        return power; 
    }

    public boolean isDazed(){
         return dazed; 
    }

    public int getPoints(){
        return points;
    }*/
}

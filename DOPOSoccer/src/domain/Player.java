package domain;

public abstract class Player extends Entity {
    protected Character_ selectedCharacter;

    protected int goals;
    protected double hp;
    protected double powerBar;
    protected Power power;
    protected boolean dazed;
    protected int speedStat;
    protected int kickStat;
    protected int jumpStat;
    protected int survivalStat;
    protected int points;


    public Player(Character_ ch) throws DopoSoccerException{
        this.selectedCharacter = ch;
        this.speedStat = 1;
        this.kickStat = 1;
        this.jumpStat = 1;
        this.survivalStat = 1;
        this.hp = getMaxHp();
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    //1 left 2 right
    public void move(int direction) throws DopoSoccerException {
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void kick() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public void jump() throws DopoSoccerException{
        throw new DopoSoccerException(DopoSoccerException.NOT_DATA);
    }

    public double getKickMultiplier(){
        return 1 + 0.10 * (kickStat - 1);
    }

    public double getMaxHp(){
        return  100 * (1 + 0.10 * (survivalStat - 1));
    } 

    public Character_ getSelectedCharacter(){
        return selectedCharacter;
    }

    public int getGoals(){
        return goals;
    }

    public double getHp(){
        return hp;
    }

    public double getPowerBar(){
        return powerBar;
    }

    public Power getPower(){
        return power; 
    }

    public boolean isDazed(){
         return dazed; 
    }

    public int getPoints(){
        return points;
    }
}

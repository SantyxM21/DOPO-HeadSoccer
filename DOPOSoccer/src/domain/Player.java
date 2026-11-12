package domain;

public abstract class Player extends Entity {
    protected Character_ selectedCharacter;
    protected Profile profile;
    protected Effect effect;

    protected int goals;
    protected double health;
    protected double powerBar;


    public Player(Character_ ch) throws DopoSoccerException{
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

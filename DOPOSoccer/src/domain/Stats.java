package domain;

public class Stats {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 10; 
    private static final int INICIAL_PRICE = 500;

    private int speed = MIN_LEVEL;
    private int kick = MIN_LEVEL;
    private int jump = MIN_LEVEL;
    private int survival = MIN_LEVEL;

    public Stats(){
    }

    public int getLevel(String stat) throws DopoSoccerException{
        switch(stat.toLowerCase().trim()){
            case "speed":
                return speed;
            case "kick":
                return kick;
            case "jump":
                return jump;
            case "survival":
                return survival;
            default:
                throw new DopoSoccerException(DopoSoccerException.INVALIDAD_STAT);
        }
    }

    public void update(){

    }

    public double getPrice(){
        return 0.0; //revisar
    }
}
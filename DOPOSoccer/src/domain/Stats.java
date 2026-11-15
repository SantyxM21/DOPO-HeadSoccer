package domain;

import java.util.Arrays;

public class Stats {
    public static final String[] STATS_NAMES = {"Velocidad", "Salto", "Patada", "Vida"};
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 10; 
    public static final int INICIAL_PRICE = 500;

    private final int[] STATS_LEVES = new int[STATS_NAMES.length];

    private int speed = MIN_LEVEL;
    private int kick = MIN_LEVEL;
    private int jump = MIN_LEVEL;
    private int survival = MIN_LEVEL;

    public Stats(){
        Arrays.fill(STATS_LEVES, MIN_LEVEL);
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

    public int[] getStats(){
        return STATS_LEVES;
    }

    public int upgradeStat(String stat, int points){
        int indexStat = Arrays.asList(STATS_NAMES).indexOf(stat);
        if(indexStat == -1) return 0; //TODO: error

        int levelStat = STATS_LEVES[indexStat];
        int costUpgrade = INICIAL_PRICE*((int)Math.pow(2, levelStat-1));
        if(points >= costUpgrade){
            STATS_LEVES[indexStat] += 1;
            return costUpgrade;
        }

        return 0;
    }

    public double getPrice(){
        return 0.0; //revisar
    }
}
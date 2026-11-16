package domain;

import java.util.Arrays;

public class Stats {
    public static final String[] STATS_NAMES = {"Velocidad", "Salto", "Patada", "Vida"};
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 10; 
    public static final int INICIAL_PRICE = 500;

    private final int[] STATS_LEVES = new int[STATS_NAMES.length];   // mismo orden que STATS_NAMES

    public Stats(){
        Arrays.fill(STATS_LEVES, MIN_LEVEL);
    }

    public int getLevel(String stat){
        switch(stat.toLowerCase().trim()){
            case "speed":
                return STATS_LEVES[0];
            case "jump":
                return STATS_LEVES[1];
            case "kick":
                return STATS_LEVES[2];
            case "survival":
                return STATS_LEVES[3];
            default:
                return 1;
        }
    }

    public int[] getStats(){
        return STATS_LEVES.clone();
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
}
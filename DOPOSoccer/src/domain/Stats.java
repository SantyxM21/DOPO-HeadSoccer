package domain;

import java.io.Serializable;
import java.util.Arrays;

public class Stats implements Serializable{
    public static final int SPEED_STAT = 0;
    public static final int JUMP_STAT = 1;
    public static final int KICK_STAT = 2;
    public static final int HEALTH_STAT = 3;
    public static final String[] STATS_NAMES = {"Velocidad", "Salto", "Patada", "Vida"};
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 10; 
    public static final int INITIAL_PRICE = 500;

    private final int[] statsLevels = new int[STATS_NAMES.length];   // mismo orden que STATS_NAMES

    public Stats(){
        Arrays.fill(statsLevels, MIN_LEVEL);
    }

    public int getLevel(int stat){
        try {
            return statsLevels[stat];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(DopoSoccerException.INVALID_STAT);
        }
    }

    public int[] getStats(){
        return statsLevels.clone();
    }

    public int getUpgradePrice(int stat){
        int levelStat = getLevel(stat);
        if (levelStat >= MAX_LEVEL) return 0;
        return INITIAL_PRICE*((int)Math.pow(2, levelStat-1));
    }

    public int upgradeStat(int stat, int points) throws DopoSoccerException{
        int costUpgrade = getUpgradePrice(stat);
        if(costUpgrade == 0) throw new DopoSoccerException(DopoSoccerException.MAX_LEVEL_REACHED);
        if(points < costUpgrade) throw new DopoSoccerException(DopoSoccerException.NOT_ENOUGH_POINTS);
        statsLevels[stat] += 1;
        return costUpgrade;
    }
}
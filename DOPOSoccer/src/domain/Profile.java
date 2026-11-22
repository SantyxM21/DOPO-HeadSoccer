package domain;

import java.io.Serializable;

public class Profile implements Serializable{
    private String name;
    private int points;

    private Stats stats;

    public Profile(String profileNumber){
        this.name = "Profile #" + profileNumber;
        this.points = 0;
        this.stats = new Stats();
    }

    public void upgradeStat(int stat) throws DopoSoccerException{
        int discountedPoints =  stats.upgradeStat(stat, points);
        this.points -= discountedPoints;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public int getPoints(){
        return points;
    }

    public int[] getStats(){
        return stats.getStats();
    }

    public String getName(){
        return name;
    }

    public int getUpgradePrice(int stat){
        return stats.getUpgradePrice(stat);
    }

    public int getStatLevel(int stat){
        return stats.getLevel(stat);
    }
}

package domain;

public class Profile {
    private String name;
    private int points;

    private Stats stats;

    public Profile(String profileNumber){
        this.name = "Profile #" + profileNumber;
        this.points = 100000;
        this.stats = new Stats();
    }

    public void upgradeStat(String stat){
        int discountedPoints =  stats.upgradeStat(stat, points);
        this.points -= discountedPoints;
    }

    public void addPoints(){

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
}

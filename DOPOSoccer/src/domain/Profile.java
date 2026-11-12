package domain;

public class Profile {
    private String name;
    private int points;

    private Stats stats;

    public Profile(String profileNumber){
        this.name = "Profile #" + profileNumber;
        this.points = 0;
        this.stats = new Stats();
    }

    public void upgrade(){

    }

    public void addPoints(){

    }

    public int getPoints(){
        return points;
    }

    public Stats getStats(){
        return stats;
    }
}

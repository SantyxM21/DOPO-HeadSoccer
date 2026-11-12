package domain;

public class Stats {
    private static final int MAX_LEVEL = 5; //revisar numero
    private static final int INICIAL_PRICE = 0;//revisar numero

    private int speed;
    private int kick;
    private int jump;
    private int health;

    public Stats(){
    }

    public int getLevel(String habilitie){
        int value = -1;
        switch(habilitie){
            case "speed":
                value = speed;
            case "kick":
                value = kick;
            case "jump":
                value = jump;
            case "health":
                value = health;
        //if (value == -1) break; Pensar en que exception poner aca
        }
        return value;
    }

    public void update(){

    }

    public double getPrice(){
        return 0.0; //revisar
    }
}
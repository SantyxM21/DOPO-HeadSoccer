package domain;

import java.awt.Dimension;

/**
 * 
 * 
 * @author Ivan Andres Bernal Sabogal
 * @author César Santiago Malaver Garnica
 * @version 05/11/2026
 */
public class DopoSoccer {

    public static final int TIME_MODE = 1;
    public static final int GOALS_MODE = 2; 

    public static final int PVSP_MODE = 1; 
    public static final int PVSM_MODE = 2; 
    

    private Profile profile1;
    private Profile profile2;
    private Match match;


    public DopoSoccer(){
        this.profile1 = new Profile("1");
        this.profile2 = new Profile("2");
    }

    public void newGame(int type, String conditions, Dimension resolution){
        if(type == TIME_MODE) match = new TimeMatch(conditions, resolution);
        else if (type == GOALS_MODE) match = new GoalsMatch(conditions, resolution);


    }

    public void addHuman(int side, String character){
        match.addHuman(side, character);
    }

    public void addMachine(int side, String character, String machineType){
    }

    public int getPlayerX(int player){
        return match.getPlayer(player).getXPosition();
    }

    public int getPlayerY(int player){
        return match.getPlayer(player).getYPosition();
    }

    public void move(int player, int direction){
        match.getPlayer(player).move(direction);
    }

    public void jump(int player){
        match.getPlayer(player).jump();
    }

    public boolean hasMatch(){
        return match != null;
    }

    public void applyGravity(int player){
        match.getPlayer(player).applyGravity();
    }

    public void activatePower(){
    }

    public void upgradeStats(){
    }
}
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

    public static final String[] COUNTRIES = {"Corea del Sur", "Camerún", "Nigeria", "Estados Unidos"};

    private Profile profile1;
    private Profile profile2;
    private Match match;


    public DopoSoccer(){
        this.profile1 = new Profile("1");
        this.profile2 = new Profile("2");
    }

    public void newGame(int type, int conditions, Dimension resolution){
        if(type == TIME_MODE) match = new TimeMatch(conditions, resolution);
        else if (type == GOALS_MODE) match = new GoalsMatch(conditions, resolution);
    }

    public void update(){
        match.update();
    }

    public void addHuman(int side, String character, Profile profile) throws DopoSoccerException{
        match.addHuman(side, character, profile);
    }

    public void addMachine(int side, String character, String machineType){
    }

    public void move(int player, int direction){
        match.getPlayer(player).move(direction);
    }

    public void jump(int player){
        match.getPlayer(player).jump();
    }

    public void kick(int player){
        match.kick(player);
    }

    public boolean hasMatch(){
        return match != null;
    }

    public void applyGravity(int player){
        match.getPlayer(player).applyGravity();
    }

    public void activatePower(){
    }


    public Profile getProfile(int side){
        if (side == Match.LEFT_SIDE) return profile1;
        else return profile2;
    }

    public int getPlayerPoints(int side){
        return getProfile(side).getPoints();
    }

    public String getPlayerName(int side){
        return getProfile(side).getName();
    }

    public int[] getPlayerStats(int side){
        return getProfile(side).getStats();
    }

    public void upgradeStats(int player, String stat){
        getProfile(player).upgradeStat(stat);
    }

    public int getPlayerSize(int player){
        return match.getPlayer(player).getSize();
    }

    public int getGroundY(){
        return match.getField().getGroundY();
    }

    public int getGoalFrameX(int side){
        return match.getField().getGoalFrame(side).getXPosition();
    }

    public int getGoalFrameY(){
        return match.getField().getGoalFrame(Match.LEFT_SIDE).getYPosition();
    }

    public int getPlayerX(int player){
        return match.getPlayer(player).getXPosition();
    }

    public int getPlayerY(int player){
        return match.getPlayer(player).getYPosition();
    }

    public int getGoalFrameWidth(){
        return match.getField().getGoalFrame(Match.LEFT_SIDE).getWidth();
    }

    public int getGoalFrameHeight(){
        return match.getField().getGoalFrame(Match.LEFT_SIDE).getHeight();
    }

    public int getBallX(){
        return match.getBall().getXPosition();
    }

    public int getBallY(){
        return match.getBall().getYPosition();
    }

    public int getBallDiameter(){
        return match.getBall().getDiameter();
    }
}
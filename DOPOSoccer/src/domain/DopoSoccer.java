package domain;

import java.awt.Dimension;
import java.io.*;

/**
 *
 *
 * @author Ivan Andres Bernal Sabogal
 * @author César Santiago Malaver Garnica
 * @version 05/11/2026
 */
public class DopoSoccer implements Serializable{

    public static final int TIME_MODE = 1;
    public static final int GOALS_MODE = 2;

    public static final int PVSP_MODE = 1;
    public static final int PVSM_MODE = 2;

    public static final String[] COUNTRIES = {Corea.NAME, Camerun.NAME, Nigeria.NAME, USA.NAME};   // el nombre lo define cada personaje

    private Profile leftProfile;
    private Profile rightProfile;
    private Match match;
    private int matchType;

    public DopoSoccer(){
        this.leftProfile = new Profile("1");
        this.rightProfile = new Profile("2");
    }

    // ==================== CICLO DE LA PARTIDA ====================

    public void newGame(int type, String conditions, Dimension resolution) throws DopoSoccerException{
        matchType = type;
        if(type == TIME_MODE) match = new TimeMatch(conditions, resolution);
        else if (type == GOALS_MODE) match = new GoalsMatch(conditions, resolution);
    }

    public boolean hasMatch(){
        return match != null;
    }

    public void update(double seconds){
        match.update(seconds);
    }

    public void endGame(){
        match = null;
    }

    // ==================== JUGADORES ====================

    public void addHuman(int side, String character, Profile profile) throws DopoSoccerException{
        match.addHuman(side, character, profile);
    }

    public void addMachine(int side, String character, Profile profile, int machineType) throws DopoSoccerException{
        match.addMachine(side, character, profile, machineType);
    }

    // ==================== ACCIONES DEL JUGADOR ====================

    public void move(int player, int direction){
        if(acceptsKeys(player)) match.getPlayer(player).move(direction);
    }

    public void jump(int player){
        if(acceptsKeys(player)) match.getPlayer(player).jump();
    }

    public void kick(int player){
        if(acceptsKeys(player)) match.kick(player);
    }

    public void activatePower(int player){
        if(acceptsKeys(player)) match.getPlayer(player).powerActivation();
    }

    private boolean acceptsKeys(int player){
        return !match.isFinished() && match.getPlayer(player).isKeyboardControlled();
    }

    // ==================== PERFILES ====================

    public Profile getProfile(int side){
        if (side == Match.LEFT_PLAYER) return leftProfile;
        else return rightProfile;
    }

    public void swapProfiles(){
        Profile temp = leftProfile;
        leftProfile = rightProfile;
        rightProfile = temp;
    }

    public String getPlayerName(int side){
        return getProfile(side).getName();
    }

    public int getPlayerPoints(int side){
        return getProfile(side).getPoints();
    }


    public int[] getPlayerStats(int side){
        return getProfile(side).getStats();
    }

    public int getUpgradePrice(int side, int stat){
        return getProfile(side).getUpgradePrice(stat);
    }

    public void upgradeStats(int player, int stat) throws DopoSoccerException{
        getProfile(player).upgradeStat(stat);
    }

    // ==================== ESTADO DE LA PARTIDA ====================

    public int getMatchType(){
        return matchType;
    }

    public int getTime(){
        return match.getTime();
    }

    public int getPlayerGoals(int side){
        return match.getPlayerGoals(side);
    }

    public boolean isFinished(){
        return match.isFinished();
    }

    public int getWinner(){
        return match.getWinner();
    }

    public String textUnderScore(){
        return match.textUnderScore();
    }

    public int getPointsEarned(int side){
        return match.getPointsEarned(side);
    }

    // ==================== ESTADO DEL JUGADOR ====================

    public int getPlayerX(int player){
        return match.getPlayer(player).getXPosition();
    }

    public int getPlayerY(int player){
        return match.getPlayer(player).getYPosition();
    }

    public int getPlayerSize(int player){
        return match.getPlayer(player).getDiameter();
    }

    public double getPowerBar(int side){
        return match.getPlayer(side).getPowerBar();
    }

    public double getHealth(int side){
        return match.getPlayer(side).getHealth();
    }

    public double getMaxHealth(int side){
        return match.getPlayer(side).getMaxHealth();
    }

    public boolean hasAura(int side){
        return match.getPlayer(side).hasAura();
    }

    public boolean hasEffect(int side){
        return match.hasEffect(side);
    }

    public String getCharacter(int side){
        return match.getPlayer(side).getSelectedCharacter().getName();
    }
    // ==================== BALÓN ====================

    public int getBallX(){
        return match.getBall().getXPosition();
    }

    public int getBallY(){
        return match.getBall().getYPosition();
    }

    public int getBallDiameter(){
        return match.getBall().getDiameter();
    }

    // Balones falsos del tiro Ilusion, se dibujan igual que el real
    public int getFakeBallCount(){
        return match.getFakeBalls().size();
    }

    public int getFakeBallX(int index){
        return match.getFakeBalls().get(index).getXPosition();
    }

    public int getFakeBallY(int index){
        return match.getFakeBalls().get(index).getYPosition();
    }

    // ==================== CANCHA ====================

    public int getGroundY(){
        return match.getField().getGroundY();
    }

    public int getGoalFrameX(int side){
        return match.getField().getGoalFrame(side).getXPosition();
    }

    public int getGoalFrameY(){
        return match.getField().getGoalFrame(Match.LEFT_PLAYER).getYPosition();
    }

    public int getGoalFrameWidth(){
        return match.getField().getGoalFrame(Match.LEFT_PLAYER).getWidth();
    }

    public int getGoalFrameHeight(){
        return match.getField().getGoalFrame(Match.LEFT_PLAYER).getHeight();
    }


    //==================== PERSISTENCIA ================================

    /**
     * Saves a file with a game with extension .dopo
     * @param file The dopo soccer game that will be saved
     * @param game The dopo soccer game saved
     * @throws DopoSoccerException INVALID_EXTENSION_DAT, FILE_NOT_FOUND, NOT_SERIALIZABLE, ERROR_WHILE_WRITING, GENERAL_ERROR
     */
    public static void saveAs(File file, DopoSoccer game) throws DopoSoccerException{
        if (!file.getName().endsWith(".dopo")) throw new DopoSoccerException(DopoSoccerException.INVALID_DOPO_EXTENSION);
        ObjectOutputStream toSave = null;

        try{
            toSave = new ObjectOutputStream(new FileOutputStream(file));
            toSave.writeObject(game);
        } catch (FileNotFoundException e){
            throw new DopoSoccerException(DopoSoccerException.FILE_NOT_FOUND, e);
        } catch (NotSerializableException e){
            throw new DopoSoccerException(DopoSoccerException.NOT_SERIALIZABLE, e);
        } catch (IOException e){
            throw new DopoSoccerException(DopoSoccerException.ERROR_WHILE_WRITING, e);
        } finally {
            if (toSave != null) {
                try{toSave.close();} 
                catch (IOException e){throw new DopoSoccerException(DopoSoccerException.ERROR_WHILE_WRITING, e);}
            }
        }
    }

    /**
     * Open a .dopo file that contains a game
     * @param file The file of the dopo soccer game that will be opened
     * @return The dopo soccer game opened
     * @throws DopoSoccerExceptionINVALID_EXTENSION_DAT, FILE_NOT_FOUND, INCOMPATIBLE_VERSION, CLASS_NOT_FOUND, ERROR_WHILE_READING
     */
    public static DopoSoccer open(File file) throws DopoSoccerException{
        if (!file.getName().endsWith(".dopo")) throw new DopoSoccerException(DopoSoccerException.INVALID_DOPO_EXTENSION);
        ObjectInputStream toOpen = null;
        try{
            toOpen = new ObjectInputStream( new FileInputStream(file));
            return (DopoSoccer) toOpen.readObject();
        } catch(FileNotFoundException e){
            throw new DopoSoccerException(DopoSoccerException.FILE_NOT_FOUND, e);
        } catch(InvalidClassException e){
            throw new DopoSoccerException(DopoSoccerException.INCOMPATIBLE_VERSION, e);
        } catch(ClassNotFoundException e){
            throw new DopoSoccerException(DopoSoccerException.CLASS_NOT_FOUND, e);
        } catch(IOException e){
            throw new DopoSoccerException(DopoSoccerException.ERROR_WHILE_READING, e);
        } finally{
            if (toOpen != null){
                try{toOpen.close();}
                catch(IOException e) {throw new DopoSoccerException(DopoSoccerException.ERROR_WHILE_READING, e);}
            }
        }
    }




}

package domain;

public class Machine extends Player {

    public static final int DUMB = 1;
    public static final int OFENSIVE = 2;
    public static final int DEFENSIVE = 3;
    public static final int EXPERT = 4;

    public static final int POSSIBLE_MOVES = 5;
    
    public Machine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile);
    }
}

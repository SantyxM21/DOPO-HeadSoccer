package domain;

public class OfensiveMachine extends Machine{
    public OfensiveMachine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile, machineType);
    }

    @Override
    public void act(Match match, double seconds){
        attack(match);
    }
}

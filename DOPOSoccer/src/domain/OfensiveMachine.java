package domain;

public class OfensiveMachine extends Machine{
    public OfensiveMachine(Character_ ch, double x, double y, Field field, Profile profile) throws DopoSoccerException{
        super(ch, x, y, field, profile);
    }

    @Override
    public void act(Match match, double seconds){
        attack(match);
    }
}

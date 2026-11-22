package domain;

public class ExpertMachine extends Machine{
    public static final double DECISION_SECONDS = 0.2;     

    private boolean defending = false;
    private double timeToDecide = 0;

    public ExpertMachine(Character_ ch, double x, double y, Field field, Profile profile) throws DopoSoccerException{
        super(ch, x, y, field, profile);
    }

    @Override
    public void act(Match match, double seconds){
        timeToDecide -= seconds;
        if (timeToDecide <= 0){                 // mantiene cada decision un rato para no cambiar de idea en cada frame
            defending = shouldDefend(match);
            timeToDecide = DECISION_SECONDS;
        }
        if (defending) defend(match);
        else attack(match);
    }

    private boolean shouldDefend(Match match){
        Ball ball = match.getBall();
        boolean rivalPowerShot = ball.hasPower() && ball.getChargedBy() != this;
        int mySide = match.sideOf(this);
        double fromMiddle = (ball.center().getX() - field.getWidth() / 2.0) * match.directionOf(this);   // negativo si esta en su mitad
        boolean ballInMyHalf = fromMiddle < 0;
        int myGoals = match.getPlayerGoals(mySide);
        int rivalGoals = match.getPlayerGoals(Match.rivalSide(mySide));

        if (rivalPowerShot) return true;            // si viene un poder va a cubrir el arco
        if (myGoals < rivalGoals) return false;     // va perdiendo entonces ataca
        return ballInMyHalf;                        // empatado o ganando --> defiende si el balon esta en su lado
    }

    public boolean isDefending(){
        return defending;
    }
}

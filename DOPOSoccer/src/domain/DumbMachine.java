package domain;

public class DumbMachine extends Machine {
    public static final double DECISION_SECONDS = 0.2;     // cuanto tiempo mantiene cada decision

    private int currentMove = 0;                            // 0 no hace nada
    private double timeToDecide = 0;

    public DumbMachine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile, machineType);
    }

    @Override
    public void act(Match match, double seconds){
        timeToDecide -= seconds;
        if (timeToDecide <= 0) {                                                // escoge al azar una nueva accion y la mantiene un rato
            currentMove = (int) (Math.random() * Machine.POSSIBLE_MOVES + 1);  // entre 1 y n, ambos incluidos
            timeToDecide = DECISION_SECONDS;
        }

        switch (currentMove) {
            case 1:
                move(1);                    // caminar se mantiene durante toda la decision
                break;
            case 2:
                move(-1);
                break;
            case 3:
                jump();
                currentMove = 0;            // saltar y activar el poder se hacen una sola vez por decision
                break;
            case 4:
                match.kick(this);
                break;
            case 5:
                powerActivation();
                currentMove = 0;
                break;
        }
    }
}

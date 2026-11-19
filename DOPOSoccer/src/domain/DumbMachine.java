package domain;

public class DumbMachine extends Machine {
    public DumbMachine(Character_ ch, double x, double y, Field field, Profile profile, int machineType) throws DopoSoccerException{
        super(ch, x, y, field, profile, machineType);
    }

    @Override 
    public void update(double seconds){
        super.update(seconds);
        int move = (int) (Math.random() * Machine.POSSIBLE_MOVES + 1);  // entre 1 y n, ambos incluidos

        switch (move) {
            case 1:
                move(1);
                break;
            case 2:
                move(-1);
                break;
            case 3:
                jump();
                break;
            case 4:
                // kick(ball, this, diameter);
                break;
            case 5:
                powerActivation();
                break;
        }

    }
}

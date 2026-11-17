package domain;

public class PushEffect extends Effect{
    private static final double PUSH_SPEED = 2.5;
    private int direction;

    public PushEffect() {
        super(1.5);
    }

    @Override
    public void applyEffect(Player target, int direction) {
        super.applyEffect(target, direction);
        this.direction = direction; // -1 izquierda 1 derecha
        target.setSpeed(0);
        target.setJumpForce(0);
        target.setKickForce(0);
    }

    @Override
    public void update(Player target, double secondsElapsed) {
        target.pushHorizontal(direction * PUSH_SPEED);
        super.update(target, secondsElapsed);
    }
}

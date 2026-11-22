package domain;

public class UnconsiousEffect extends Effect{
    private static final double LAUNCH_SPEED = 8; 
    private double pushPerFrame;

    public UnconsiousEffect() {
        super(3.0);
    }

    @Override
    public void applyEffect(Player target, int direction, Field field) {
        super.applyEffect(target, direction, field);
        target.setVelocity(new Vector2D(0, -field.scale(LAUNCH_SPEED)));      // negativo para levantarlo
        target.immobilize();                                    // no puede moverse, saltar ni patear
    }
}

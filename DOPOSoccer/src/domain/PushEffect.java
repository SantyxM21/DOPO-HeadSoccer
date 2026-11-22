package domain;

public class PushEffect extends Effect{
    private static final double PUSH_SPEED = 2.5;
    private double pushPerFrame;

    public PushEffect() {
        super(1.5);
    }

    @Override
    public void applyEffect(Player target, int direction, Field field) {
        super.applyEffect(target, direction, field);
        pushPerFrame = direction * field.scale(PUSH_SPEED); // -1 izquierda 1 derecha
        target.immobilize();                                    // no puede moverse, saltar ni patear
    }

    @Override
    public void update(Player target, double secondsElapsed) {
        target.translate(pushPerFrame, 0);                                          // translate no lo deja salir del campo
        target.setVelocity(new Vector2D(pushPerFrame, target.getVelocity().getY()));
        super.update(target, secondsElapsed);
    }
}

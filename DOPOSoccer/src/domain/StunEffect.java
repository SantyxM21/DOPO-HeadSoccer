package domain;

public class StunEffect extends Effect{
    public static final double STUN_SECONDS = 3.0;

    public StunEffect() {
        super(STUN_SECONDS);
    }

    @Override
    public void applyEffect(Player target, int direction) {
        super.applyEffect(target, direction);
        target.setSpeed(0);
        target.setJumpForce(0);
        target.setKickForce(0);
    }

    @Override
    public void end(Player target) {
        super.end(target);
        target.restoreHealth();     // al terminar el aturdimiento vuelve con la vida al maximo
    }
}

package domain;

public class ElectroEffect extends Effect{
    public ElectroEffect() {
        super(3.0);
    }

    @Override
    public void applyEffect(Player target, int direction) {
        super.applyEffect(target, direction);               
        target.setSpeed(target.getSpeed()/2);               // se mueve al 50 % de su velocidad
        target.setJumpForce(0);                  // no puede saltar
    }
}

package domain;

public class ElectroEffect extends Effect{
    public ElectroEffect() {
        super(3.0);
    }

    @Override
    public void applyEffect(Player target, int direction, Field field) {
        super.applyEffect(target, direction, field);               
        target.slowDown(0.5);               // se mueve al 50 % de su velocidad
        target.blockJump();                // no puede saltar
    }
}

package domain;

import java.util.ArrayList;
import java.util.List;

public class Ilusion extends Power {
    public static final int FAKE_BALLS = 7;
    public static final double SPREAD_SPEED = 1.2;  // diferencia de velocidad vertical entre balones vecinos

    public Ilusion(){
        super("Ilusión", 30);
    }

    @Override
    protected Effect createEffect(){
        return new ConfusionEffect();
    }

    // Reparte los 8 balones en abanico
    // el real toma un lugar al azar para que no se pueda adivinar cual es
    @Override
    public List<FakeBall> createFakes(Ball real, Player shooter, int direction, Field field){
        Vector2D shot = shotVelocity(direction, field);
        int realSlot = (int) (Math.random() * (FAKE_BALLS + 1));
        List<FakeBall> fakes = new ArrayList<>();
        for (int slot = 0; slot <= FAKE_BALLS; slot++){
            double ySpeed = shot.getY() + (slot - FAKE_BALLS / 2.0) * field.scale(SPREAD_SPEED);
            Vector2D velocity = new Vector2D(shot.getX(), ySpeed);
            if (slot == realSlot) real.setVelocity(velocity);
            else fakes.add(new FakeBall(field, this, shooter, direction, velocity, getDurationEffect()));    // desaparecen al terminar el efecto
        }
        return fakes;
    }
}

package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class PushEffectTest {
    private Player player;
    private PushEffect effect;

    @BeforeEach
    public void setUp() throws DopoSoccerException {
        Field field = new Field(new Dimension(800, 600));
        player = new Human("Corea del Sur", 400, 500, field);
        effect = new PushEffect();
    }

    @Test
    public void shouldFinishAfterItsDuration() {
        effect.update(1.0);
        assertEquals(0.5, effect.getTimeLeft(), 0.001);
        assertFalse(effect.isFinished());

        effect.update(1.0);
        assertEquals(0, effect.getTimeLeft(), 0.001);
        assertTrue(effect.isFinished());
    }

    @Test
    public void shouldRestartTimeWhenApplied() {
        effect.update(1.0);
        effect.applyEffect(player, 1);
        assertEquals(1.5, effect.getTimeLeft(), 0.001);
    }

    @Test
    public void shouldPushPlayerInTheGivenDirection() {
        effect.applyEffect(player, 1);
        int xBeforePushRight = player.getXPosition();
        effect.tick(player);
        assertTrue(player.getXPosition() > xBeforePushRight);

        effect.applyEffect(player, -1);
        int xBeforePushLeft = player.getXPosition();
        effect.tick(player);
        assertTrue(player.getXPosition() < xBeforePushLeft);
    }
}

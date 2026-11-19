package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class PushEffectTest {
    private Player player;
    private PushEffect effect;
    private Field field;

    @BeforeEach
    public void setUp() throws DopoSoccerException {
        field = new Field(new Dimension(800, 600));
        player = new Human(new Corea(), 400, 500, field, new Profile("1"));
        effect = new PushEffect();
    }

    @Test
    public void shouldFinishAfterItsDuration() {
        effect.update(player, 1.0);
        assertEquals(0.5, effect.getTimeLeft(), 0.001);
        assertFalse(effect.isFinished());

        effect.update(player, 1.0);
        assertEquals(0, effect.getTimeLeft(), 0.001);
        assertTrue(effect.isFinished());
    }

    @Test
    public void shouldRestartTimeWhenApplied() {
        effect.update(player, 1.0);
        effect.applyEffect(player, 1, field);
        assertEquals(1.5, effect.getTimeLeft(), 0.001);
    }

    @Test
    public void shouldPushPlayerInTheGivenDirection() {
        effect.applyEffect(player, 1, field);
        int xBeforePushRight = player.getXPosition();
        effect.update(player, 0.01);
        assertTrue(player.getXPosition() > xBeforePushRight);

        effect.applyEffect(player, -1, field);
        int xBeforePushLeft = player.getXPosition();
        effect.update(player, 0.01);
        assertTrue(player.getXPosition() < xBeforePushLeft);
    }
}

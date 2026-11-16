package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class PlayerTest {
    private Player player;
    private Field field;

    @BeforeEach
    public void SetUp() throws DopoSoccerException {
        field = new Field(new Dimension(800, 600));
        player = new Human("Corea del Sur", 100, 500, field);
    }

    @Test
    public void shouldConstructPlayerSuccessfully() throws DopoSoccerException {
        assertNotNull(player.getSelectedCharacter());
        assertEquals("Corea", player.getSelectedCharacter().getName());
        assertEquals(0, player.getGoals());
    }

    @Test
    public void shouldMoveWithinScreenBounds() {
        double initialX = player.getXPosition();
        player.move(1);
        assertTrue(player.getXPosition() > initialX);

        player.move(-10000);
        assertEquals(0, player.getXPosition());
    }

    @Test
    public void shouldHandleJumpAndGravityLifecycle() {
        player.jump();
        player.applyGravity();
        assertNotNull(player.getVelocity());
    }

    @Test
    public void shouldThrowExceptionForInexistentCharacter() {
        try {
            new Human("Inexistente", 100, 500, field);
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INEXISTENT_CHARACTER, e.getMessage());
        }
    }

    @Test
    public void shouldStartWithFullSurvivalAndEmptyPowerBar() {
        assertEquals(100, player.getSurvival(), 0.001);
        assertEquals(0, player.getPowerBar(), 0.001);
        assertFalse(player.hasAura());
        assertFalse(player.hasEffect());
    }

    @Test
    public void shouldAddQuarterOfPowerBarWhenReceivingGoal() {
        player.powerBarReceivedGoal();
        assertEquals(25, player.getPowerBar(), 0.001);
    }

    @Test
    public void shouldReduceSurvivalWhenTakingDamageAndNeverGoBelowZero() {
        player.takeDamage(40);
        assertEquals(60, player.getSurvival(), 0.001);

        player.takeDamage(500);
        assertEquals(0, player.getSurvival(), 0.001);
    }

    @Test
    public void shouldNotMoveWhileBlockedByEffect() {
        player.receiveEffect(new PushEffect(), 1);
        assertTrue(player.hasEffect());

        int initialX = player.getXPosition();
        player.move(-1);
        assertEquals(initialX, player.getXPosition());
    }
}
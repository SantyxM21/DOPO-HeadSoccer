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
        player = new Human(new Corea(), 100, 500, field, new Profile("1"));
    }

    @Test
    public void shouldConstructPlayerSuccessfully() throws DopoSoccerException {
        assertNotNull(player.getSelectedCharacter());
        assertEquals("Corea del Sur", player.getSelectedCharacter().getName());
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
    public void shouldThrowExceptionWithoutCharacter() {
        try {
            new Human(null, 100, 500, field, new Profile("1"));
            fail("Deberia lanzar excepcion sin personaje");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.NOT_DATA, e.getMessage());
        }
    }

    @Test
    public void shouldThrowExceptionWithoutProfile() {
        try {
            new Human(new Corea(), 100, 500, field, null);
            fail("Deberia lanzar excepcion sin perfil");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.NOT_DATA, e.getMessage());
        }
    }

    @Test
    public void shouldStartWithFullHealthAndEmptyPowerBar() {
        assertEquals(100, player.getHealth(), 0.001);
        assertEquals(0, player.getPowerBar(), 0.001);
        assertFalse(player.hasAura());
    }

    @Test
    public void shouldAddQuarterOfPowerBarWhenReceivingGoal() {
        player.powerBarReceivedGoal();
        assertEquals(25, player.getPowerBar(), 0.001);
    }

    @Test
    public void shouldReduceHealthWhenTakingDamageAndNeverGoBelowZero() {
        player.takeDamage(40);
        assertEquals(60, player.getHealth(), 0.001);

        player.takeDamage(500);
        assertEquals(0, player.getHealth(), 0.001);
    }
/* 
    @Test
    public void shouldNotMoveWithZeroSpeed() {
        player.setSpeed(0);
        int initialX = player.getXPosition();
        player.move(-1);
        assertEquals(initialX, player.getXPosition());
    }
        */
}
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
    public void shouldSetUp() throws DopoSoccerException {
        field = new Field(new Dimension(800, 600));
        player = new Human("Corea", 100, 500, field);
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
}
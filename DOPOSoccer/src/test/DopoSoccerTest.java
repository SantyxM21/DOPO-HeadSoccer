package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import domain.*;

/**
 * The test class DopoSoccerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DopoSoccerTest{
    private DopoSoccer dopoSoccer;

    @BeforeEach
    public void SetUp() {
        dopoSoccer = new DopoSoccer();
    }

    @Test
    public void shouldIConstructWithProfiles() {
        assertNotNull(dopoSoccer.getProfile(Match.LEFT_PLAYER));
        assertNotNull(dopoSoccer.getProfile(Match.RIGHT_PLAYER));
        assertFalse(dopoSoccer.hasMatch());
    }

    @Test
    public void shouldSwapProfilesBetweenSides() {
        Profile left = dopoSoccer.getProfile(Match.LEFT_PLAYER);
        Profile right = dopoSoccer.getProfile(Match.RIGHT_PLAYER);

        dopoSoccer.swapProfiles();

        assertSame(right, dopoSoccer.getProfile(Match.LEFT_PLAYER));
        assertSame(left, dopoSoccer.getProfile(Match.RIGHT_PLAYER));
    }

    @Test
    public void shouldCreateNewTimeMatchSuccessfully() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.TIME_MODE, "60", new Dimension(800, 600));
        assertTrue(dopoSoccer.hasMatch());
        assertFalse(dopoSoccer.isFinished());
    }

    @Test
    public void shouldEndGameProperly() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", new Dimension(800, 600));
        assertTrue(dopoSoccer.hasMatch());
        dopoSoccer.endGame();
        assertFalse(dopoSoccer.hasMatch());
    }

    @Test
    public void shouldActivatePowerOnlyWhenPowerBarIsFull() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", new Dimension(800, 600));
        dopoSoccer.addHuman(Match.LEFT_PLAYER, "Corea del Sur", dopoSoccer.getProfile(Match.LEFT_PLAYER));
        dopoSoccer.addHuman(Match.RIGHT_PLAYER, "Camerún", dopoSoccer.getProfile(Match.RIGHT_PLAYER));

        dopoSoccer.activatePower(Match.LEFT_PLAYER);
        assertFalse(dopoSoccer.hasAura(Match.LEFT_PLAYER));

        for (int i = 0; i < Match.FPS * 10 + 5; i++) dopoSoccer.update(Match.FRAME_SECONDS);
        assertEquals(100, dopoSoccer.getPowerBar(Match.LEFT_PLAYER), 0.001);

        dopoSoccer.activatePower(Match.LEFT_PLAYER);
        assertTrue(dopoSoccer.hasAura(Match.LEFT_PLAYER));
        assertEquals(0, dopoSoccer.getPowerBar(Match.LEFT_PLAYER), 0.001);
    }
}
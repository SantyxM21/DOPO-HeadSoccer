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
        assertNotNull(dopoSoccer.getProfile(Match.LEFT_SIDE));
        assertNotNull(dopoSoccer.getProfile(Match.RIGHT_SIDE));
        assertFalse(dopoSoccer.hasMatch());
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
        dopoSoccer.addHuman(Match.LEFT_SIDE, "Corea del Sur", dopoSoccer.getProfile(Match.LEFT_SIDE));
        dopoSoccer.addHuman(Match.RIGHT_SIDE, "Camerún", dopoSoccer.getProfile(Match.RIGHT_SIDE));

        dopoSoccer.activatePower(Match.LEFT_SIDE);
        assertFalse(dopoSoccer.hasAura(Match.LEFT_SIDE));

        for (int i = 0; i < Match.FPS * 10 + 5; i++) dopoSoccer.update(Match.FRAME_SECONDS);
        assertEquals(100, dopoSoccer.getPowerBar(Match.LEFT_SIDE), 0.001);

        dopoSoccer.activatePower(Match.LEFT_SIDE);
        assertTrue(dopoSoccer.hasAura(Match.LEFT_SIDE));
        assertEquals(0, dopoSoccer.getPowerBar(Match.LEFT_SIDE), 0.001);
    }
}
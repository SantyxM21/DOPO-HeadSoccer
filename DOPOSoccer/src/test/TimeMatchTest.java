package test;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;
import domain.*;

public class TimeMatchTest {
    @Test
    public void shouldConstructTimeMatchWithValidCondition() throws DopoSoccerException {
        TimeMatch match = new TimeMatch("2", new Dimension(800, 600));
        assertEquals(120, match.getSecondsLeft());
        assertEquals("2:00", match.textUnderScore());
    }

    @Test
    public void shouldThrowExceptionWhenTimeConditionIsInvalid() {
        try {
            new TimeMatch("-1", new Dimension(800, 600));
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_TIME, e.getMessage());
        }

        try {
            new TimeMatch("invalido", new Dimension(800, 600));
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_TIME, e.getMessage());
        }
    }

    @Test
    public void shouldAdvanceTimeAndFormatCorrectly() throws DopoSoccerException {
        TimeMatch match = new TimeMatch("1", new Dimension(800, 600)); 
        match.addHuman(Match.LEFT_PLAYER, "Corea del Sur", new Profile("p1"));
        match.addHuman(Match.RIGHT_PLAYER, "Camerún", new Profile("p2"));

        assertEquals(60, match.getSecondsLeft());
        assertEquals("1:00", match.textUnderScore());

        for (int i = 0; i < Match.FPS * 15 + 1; i++) match.update(Match.FRAME_SECONDS);
        assertEquals(45, match.getSecondsLeft());
        assertEquals("0:45", match.textUnderScore());
    }

    @Test
    public void shouldHandleSuddenDeathWhenTimeRunsOutWithoutWinner() throws DopoSoccerException {
        TimeMatch match = new TimeMatch("1", new Dimension(800, 600));
        
        Profile p1 = new Profile("P1");
        Profile p2 = new Profile("P2");
        match.addHuman(Match.LEFT_PLAYER, "Corea del Sur", p1);
        match.addHuman(Match.RIGHT_PLAYER, "Camerún", p2);
        
        for (int i = 0; i < Match.FPS * 60 + 5; i++) match.update(Match.FRAME_SECONDS);

        assertTrue(match.isSuddenDeath());
        assertEquals("MUERTE SUBITA", match.textUnderScore());
        assertFalse(match.isFinished());
    }
}

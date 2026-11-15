package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class MatchTest {
    private GoalsMatch match;

    @BeforeEach
    public void SetUp() throws DopoSoccerException {
        match = new GoalsMatch("3", new Dimension(800, 600));
    }

    @Test
    public void shouldConstructMatchComponentsThroughSubclass() {
        assertNotNull(match.getField());
        assertNotNull(match.getBall());
        assertFalse(match.isFinished());
        assertEquals(0, match.getWinner());
    }

    @Test
    public void shouldThrowExceptionWhenAddingHumanToInvalidSide() {
        Profile profile = new Profile("Test");
        try {
            match.addHuman(99, "Corea", profile);
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_SITE, e.getMessage());
        }
    }
}

package test;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;
import domain.*;

public class GoalsMatchTest {
    
        @Test
        public void shouldConstructGoalsMatchWithValidCondition() throws DopoSoccerException {
            GoalsMatch match = new GoalsMatch("5", new Dimension(800, 600));
            assertEquals(5, match.getNumGoals());
            assertEquals("Goles para ganar 5", match.textUnderScore());
        }
    
        @Test
    public void shouldThrowExceptionWhenConditionIsInvalid() {
        try {
            new GoalsMatch("-2", new Dimension(800, 600));
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_GOALS, e.getMessage());
        }

        try {
            new GoalsMatch("abc", new Dimension(800, 600));
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_GOALS, e.getMessage());
        }
    }
}

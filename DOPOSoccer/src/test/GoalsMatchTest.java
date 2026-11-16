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

    @Test
    public void shouldFinishWhenPlayerReachesGoalsToWin() throws DopoSoccerException {
        GoalsMatch match = new GoalsMatch("1", new Dimension(800, 600));
        match.addHuman(Match.LEFT_SIDE, "Corea del Sur", new Profile("1"));
        match.addHuman(Match.RIGHT_SIDE, "Camerún", new Profile("2"));

        GoalFrame leftGoal = match.getField().getGoalFrame(Match.LEFT_SIDE);
        Ball ball = match.getBall();
        Vector2D ballCenter = ball.center();
        ball.translate(leftGoal.getXPosition() + leftGoal.getWidth() / 2 - ballCenter.getX(), leftGoal.getYPosition() + 20 - ballCenter.getY());

        match.update();

        assertTrue(match.isFinished());
        assertEquals(Match.RIGHT_SIDE, match.getWinner());
        assertEquals(Match.WINNER_POINTS, match.getPointsEarned(Match.RIGHT_SIDE));
    }
}

package test;

import domain.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;

import org.junit.jupiter.api.BeforeEach;

public class FieldTest {
    private Field field;

    @BeforeEach
    public void SetUp() {
        field = new Field(new Dimension(800, 600));
    }

    @Test
    public void shouldCalculateDimensionsAndScaleCorrectly() {
        field = new Field(new Dimension(1280, 720)); 

        assertEquals(1280, field.getWidth());
        assertEquals(720, field.getHeight());
        assertEquals(2.0, field.scale(2.0), 0.001); 
    }

    @Test
    public void shouldDetectGoalConditionsCorrectly() {
        GoalFrame leftGoal = field.getGoalFrame(Match.LEFT_PLAYER);
        double goalX = leftGoal.getXPosition();
        double goalY = leftGoal.getYPosition();

        assertTrue(field.isGoal(Match.LEFT_PLAYER, goalX, goalY + 10));
        assertFalse(field.isGoal(Match.LEFT_PLAYER, -50, goalY));
    }
}

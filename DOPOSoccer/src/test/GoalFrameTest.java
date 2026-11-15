package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import domain.*;

public class GoalFrameTest {
    @Test
    public void shouldConstructGoalFrameDimensions() {
        GoalFrame goalFrame = new GoalFrame(10, 20, 80, 150);
        assertEquals(10, goalFrame.getXPosition());
        assertEquals(20, goalFrame.getYPosition());
        assertEquals(80, goalFrame.getWidth());
        assertEquals(150, goalFrame.getHeight());
    }
}

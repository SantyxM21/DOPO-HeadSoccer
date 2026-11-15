package test;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.*;
import domain.*;

import domain.Ball;

public class EntityTest {
    @Test
    public void shouldInheritateAndManageCoordinatesCorrectlyThroughSubclass() {
        Field field = new Field(new Dimension(800, 600));
        Ball ball = new Ball(field);
        
        assertTrue(ball.getXPosition() >= 0);
        assertTrue(ball.getYPosition() >= 0);
    }
}

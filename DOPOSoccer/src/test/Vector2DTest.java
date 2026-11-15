package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Vector2D;

public class Vector2DTest {

    @Test
    public void shouldConstructAndGet2DVector() {
        Vector2D vector = new Vector2D(5.5, 10.2);
        assertEquals(5.5, vector.getX());
        assertEquals(10.2, vector.getY());
    }

    @Test
    public void shouldSetVector2DValues() {
        Vector2D vector = new Vector2D(0, 0);
        vector.setX(3.1);
        vector.setY(4.2);
        assertEquals(3.1, vector.getX());
        assertEquals(4.2, vector.getY());
    }
}

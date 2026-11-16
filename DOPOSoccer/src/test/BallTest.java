package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;

import domain.*;



public class BallTest {
    Field field;
    private Ball ball;

    @BeforeEach
    public void setUp(){
        field = new Field(new Dimension(800, 600));
        ball = new Ball(field);
    }

    @Test
    public void shouldCorrectlyInitializeBallAtKickOff(){
        assertEquals(field.getWidth() / 2 - ball.getDiameter() / 2, ball.getXPosition());
        assertEquals(field.getHeight() / 4, ball.getYPosition());
    }

    @Test
    public void shouldTranslateBallPosition(){
        int initialX = ball.getXPosition();
        int initialY = ball.getYPosition();
        ball.translate(10, 20);
        assertEquals(initialX + 10, ball.getXPosition());
        assertEquals(initialY + 20, ball.getYPosition());
    }

    @Test
    public void shouldKeepBallInsideFieldBounds(){
        ball.translate(-5000, -5000);
        ball.keepInsideField();
        assertEquals(0, ball.getXPosition());
        assertEquals(0, ball.getYPosition());
    }

    @Test
    public void shouldCalculateCorrectBallCenter(){
        Vector2D center = ball.center();
        assertEquals(ball.getXPosition() + ball.getDiameter() / 2.0, center.getX(), 0.001);
        assertEquals(ball.getYPosition() + ball.getDiameter() / 2.0, center.getY(), 0.001);
    }

    @Test
    public void shouldChargePowerAndUseCoreanPowerEffect() throws DopoSoccerException {
        Player player = new Human("Corea del Sur", 100, 500, field);
        Power power = player.getSelectedCharacter().getPower();

        ball.chargePower(power, player, 1);

        assertEquals(power, ball.getChargedPower());
        assertEquals(player, ball.getChargedBy());
        assertEquals(0, ball.getVelocity().getY(), 0.001);
    }

    @Test
    public void shouldClearChargeAndClearItOnKickOff() throws DopoSoccerException {
        Player player = new Human("Corea del Sur", 100, 500, field);
        ball.chargePower(player.getSelectedCharacter().getPower(), player, 1);
        ball.clearCharge();
        assertNull(ball.getChargedPower());
        assertNull(ball.getChargedBy());

        ball.chargePower(player.getSelectedCharacter().getPower(), player, 1);
        ball.kickOff();
        assertNull(ball.getChargedPower());
    }
}

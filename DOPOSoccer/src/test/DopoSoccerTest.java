package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import domain.*;

/**
 * Pruebas de la fachada DopoSoccer:
 *
 * @author Ivan Andres Bernal Sabogal
 * @author César Santiago Malaver Garnica
 * @version 26/11/2026
 */
public class DopoSoccerTest {
    private static final Dimension RESOLUTION = new Dimension(800, 600);
    private static final int LEFT = Match.LEFT_PLAYER;
    private static final int RIGHT = Match.RIGHT_PLAYER;

    private DopoSoccer dopoSoccer;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        dopoSoccer = new DopoSoccer();
    }

    /* Crea una partida por goles con dos humanos */
    private void startHumanGame() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", RESOLUTION);
        dopoSoccer.addHuman(LEFT, Corea.NAME, dopoSoccer.getProfile(LEFT));
        dopoSoccer.addHuman(RIGHT, Camerun.NAME, dopoSoccer.getProfile(RIGHT));
    }

    /* Avanza la partida la cantidad de segundos dada, frame a frame */
    private void play(double seconds) {
        int frames = (int) Math.ceil(seconds * Match.FPS);
        for (int i = 0; i < frames; i++) dopoSoccer.update(Match.FRAME_SECONDS);
    }

    // ==================== CONSTRUCTOR / getProfile ====================

    @Test
    public void shouldCreateTwoDifferentProfilesWithoutPoints() {
        Profile left = dopoSoccer.getProfile(LEFT);
        Profile right = dopoSoccer.getProfile(RIGHT);
        assertNotNull(left);
        assertNotNull(right);
        assertNotSame(left, right);
        assertEquals(0, dopoSoccer.getPlayerPoints(LEFT));
        assertEquals(0, dopoSoccer.getPlayerPoints(RIGHT));
    }

    @Test
    public void shouldNotHaveMatchWhenCreated() {
        assertFalse(dopoSoccer.hasMatch());
        assertEquals("Profile #1", dopoSoccer.getPlayerName(LEFT));
        assertEquals("Profile #2", dopoSoccer.getPlayerName(RIGHT));
    }

    // ==================== newGame ====================

    @Test
    public void shouldCreateValidMatchesOfBothModes() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.TIME_MODE, "1", RESOLUTION);
        assertTrue(dopoSoccer.hasMatch());
        assertEquals(DopoSoccer.TIME_MODE, dopoSoccer.getMatchType());

        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "5", RESOLUTION);
        assertTrue(dopoSoccer.hasMatch());
        assertEquals(DopoSoccer.GOALS_MODE, dopoSoccer.getMatchType());
        assertEquals("Goles para ganar 5", dopoSoccer.textUnderScore());
    }

    @Test
    public void shouldNotCreateMatchWithInvalidConditions() {
        try {
            dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "-2", RESOLUTION);
            fail("Debio lanzar excepcion por goles invalidos");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_GOALS, e.getMessage());
        }

        try {
            dopoSoccer.newGame(DopoSoccer.TIME_MODE, "abc", RESOLUTION);
            fail("Debio lanzar excepcion por tiempo invalido");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_TIME, e.getMessage());
        }
        assertFalse(dopoSoccer.hasMatch());
    }

    // ==================== endGame ====================

    @Test
    public void shouldRemoveMatchWhenEndingGame() throws DopoSoccerException {
        startHumanGame();
        dopoSoccer.endGame();
        assertFalse(dopoSoccer.hasMatch());
    }

    @Test
    public void shouldKeepProfilesAfterEndingGame() throws DopoSoccerException {
        Profile left = dopoSoccer.getProfile(LEFT);
        Profile right = dopoSoccer.getProfile(RIGHT);
        startHumanGame();
        dopoSoccer.endGame();
        assertSame(left, dopoSoccer.getProfile(LEFT));
        assertSame(right, dopoSoccer.getProfile(RIGHT));
    }

    // ==================== addHuman ====================

    @Test
    public void shouldAddHumansWithSelectedCharacters() throws DopoSoccerException {
        startHumanGame();
        assertEquals(Corea.NAME, dopoSoccer.getCharacter(LEFT));
        assertEquals(Camerun.NAME, dopoSoccer.getCharacter(RIGHT));
        assertTrue(dopoSoccer.getPlayerX(LEFT) < dopoSoccer.getPlayerX(RIGHT));
        assertEquals(dopoSoccer.getMaxHealth(LEFT), dopoSoccer.getHealth(LEFT), 0.001);
    }

    @Test
    public void shouldNotAddHumanWithInexistentCharacter() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", RESOLUTION);
        try {
            dopoSoccer.addHuman(LEFT, "Colombia", dopoSoccer.getProfile(LEFT));
            fail("Debio lanzar excepcion por personaje inexistente");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INEXISTENT_CHARACTER, e.getMessage());
        }
    }

    // ==================== addMachine ====================

    @Test
    public void shouldAddEveryTypeOfMachine() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", RESOLUTION);
        dopoSoccer.addHuman(LEFT, Nigeria.NAME, dopoSoccer.getProfile(LEFT));
        int[] types = {Machine.DUMB, Machine.OFENSIVE, Machine.DEFENSIVE, Machine.EXPERT};
        for (int type : types) {
            dopoSoccer.addMachine(RIGHT, USA.NAME, dopoSoccer.getProfile(RIGHT), type);
            assertEquals(USA.NAME, dopoSoccer.getCharacter(RIGHT));
            play(1);
        }
    }

    @Test
    public void shouldNotAddMachineOfInexistentType() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", RESOLUTION);
        try {
            dopoSoccer.addMachine(RIGHT, USA.NAME, dopoSoccer.getProfile(RIGHT), 99);
            fail("Debio lanzar excepcion por tipo de maquina inexistente");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_MACHINE, e.getMessage());
        }
    }

    // ==================== move ====================

    @Test
    public void shouldMoveHumanInBothDirections() throws DopoSoccerException {
        startHumanGame();
        int start = dopoSoccer.getPlayerX(LEFT);
        for (int i = 0; i < 10; i++) dopoSoccer.move(LEFT, 1);
        assertTrue(dopoSoccer.getPlayerX(LEFT) > start);

        int afterRight = dopoSoccer.getPlayerX(LEFT);
        for (int i = 0; i < 10; i++) dopoSoccer.move(LEFT, -1);
        assertTrue(dopoSoccer.getPlayerX(LEFT) < afterRight);
    }

    @Test
    public void shouldIgnoreKeyboardMoveForMachine() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.GOALS_MODE, "3", RESOLUTION);
        dopoSoccer.addHuman(LEFT, Corea.NAME, dopoSoccer.getProfile(LEFT));
        dopoSoccer.addMachine(RIGHT, Camerun.NAME, dopoSoccer.getProfile(RIGHT), Machine.DUMB);
        int start = dopoSoccer.getPlayerX(RIGHT);
        for (int i = 0; i < 10; i++) dopoSoccer.move(RIGHT, -1);
        assertEquals(start, dopoSoccer.getPlayerX(RIGHT));
    }

    // ==================== jump ====================

    @Test
    public void shouldJumpAndFallBackToGround() throws DopoSoccerException {
        startHumanGame();
        int groundY = dopoSoccer.getPlayerY(LEFT);
        dopoSoccer.jump(LEFT);
        dopoSoccer.update(Match.FRAME_SECONDS);
        assertTrue(dopoSoccer.getPlayerY(LEFT) < groundY);

        play(3);
        assertEquals(groundY, dopoSoccer.getPlayerY(LEFT));
    }

    @Test
    public void shouldNotJumpAgainWhileInTheAir() throws DopoSoccerException {
        startHumanGame();
        dopoSoccer.jump(LEFT);
        dopoSoccer.update(Match.FRAME_SECONDS);
        dopoSoccer.update(Match.FRAME_SECONDS);
        int beforeSecondJump = dopoSoccer.getPlayerY(LEFT);

        // si el segundo salto se aceptara, la velocidad volveria a ser la de despegue y subiria mas rapido
        DopoSoccer reference = new DopoSoccer();
        reference.newGame(DopoSoccer.GOALS_MODE, "3", RESOLUTION);
        reference.addHuman(LEFT, Corea.NAME, reference.getProfile(LEFT));
        reference.addHuman(RIGHT, Camerun.NAME, reference.getProfile(RIGHT));
        reference.jump(LEFT);
        for (int i = 0; i < 3; i++) reference.update(Match.FRAME_SECONDS);

        dopoSoccer.jump(LEFT);
        dopoSoccer.update(Match.FRAME_SECONDS);
        assertTrue(dopoSoccer.getPlayerY(LEFT) <= beforeSecondJump);
        assertEquals(reference.getPlayerY(LEFT), dopoSoccer.getPlayerY(LEFT));
    }

    // ==================== activatePower ====================

    @Test
    public void shouldNotActivatePowerWithoutFullPowerBar() throws DopoSoccerException {
        startHumanGame();
        dopoSoccer.activatePower(LEFT);
        assertFalse(dopoSoccer.hasAura(LEFT));
        assertEquals(0, dopoSoccer.getPowerBar(LEFT), 0.001);
    }

    @Test
    public void shouldActivatePowerWhenPowerBarIsFull() throws DopoSoccerException {
        startHumanGame();
        play(Player.POWER_BAR_CHARGE_SECONDS + 0.1);
        assertEquals(Player.MAX_POWER_BAR, dopoSoccer.getPowerBar(LEFT), 0.001);

        dopoSoccer.activatePower(LEFT);
        assertTrue(dopoSoccer.hasAura(LEFT));
        assertEquals(0, dopoSoccer.getPowerBar(LEFT), 0.001);
    }

    // ==================== update ====================

    @Test
    public void shouldAdvanceTimeWhenUpdating() throws DopoSoccerException {
        startHumanGame();
        assertEquals(0, dopoSoccer.getTime());
        play(2.1);   // un poco mas de 2 s para evitar el error de redondeo al sumar FRAME_SECONDS
        assertEquals(2, dopoSoccer.getTime());
    }

    @Test
    public void shouldChargePowerBarWhenUpdating() throws DopoSoccerException {
        startHumanGame();
        play(Player.POWER_BAR_CHARGE_SECONDS / 2);
        assertEquals(Player.MAX_POWER_BAR / 2, dopoSoccer.getPowerBar(LEFT), 1.5);
        assertEquals(Player.MAX_POWER_BAR / 2, dopoSoccer.getPowerBar(RIGHT), 1.5);
    }

    // ==================== isFinished / textUnderScore ====================

    @Test
    public void shouldNotBeFinishedAtTheBeginning() throws DopoSoccerException {
        startHumanGame();
        assertFalse(dopoSoccer.isFinished());
        assertEquals(0, dopoSoccer.getWinner());
        assertEquals(0, dopoSoccer.getPointsEarned(LEFT));
    }

    @Test
    public void shouldGoToSuddenDeathWhenTimeEndsTied() throws DopoSoccerException {
        dopoSoccer.newGame(DopoSoccer.TIME_MODE, "0.02", RESOLUTION);   // 2 segundos
        dopoSoccer.addHuman(LEFT, Corea.NAME, dopoSoccer.getProfile(LEFT));
        dopoSoccer.addHuman(RIGHT, Camerun.NAME, dopoSoccer.getProfile(RIGHT));
        assertEquals("0:02", dopoSoccer.textUnderScore());

        play(2.5);
        assertFalse(dopoSoccer.isFinished());
        assertEquals("MUERTE SUBITA", dopoSoccer.textUnderScore());
    }

    // ==================== swapProfiles ====================

    @Test
    public void shouldSwapProfilesBetweenSides() {
        Profile left = dopoSoccer.getProfile(LEFT);
        Profile right = dopoSoccer.getProfile(RIGHT);
        dopoSoccer.swapProfiles();
        assertSame(right, dopoSoccer.getProfile(LEFT));
        assertSame(left, dopoSoccer.getProfile(RIGHT));
    }

    @Test
    public void shouldRestoreProfilesWhenSwappingTwice() {
        Profile left = dopoSoccer.getProfile(LEFT);
        Profile right = dopoSoccer.getProfile(RIGHT);
        dopoSoccer.swapProfiles();
        dopoSoccer.swapProfiles();
        assertSame(left, dopoSoccer.getProfile(LEFT));
        assertSame(right, dopoSoccer.getProfile(RIGHT));
    }

    // ==================== upgradeStats ====================

    @Test
    public void shouldUpgradeStatAndDiscountPoints() throws DopoSoccerException {
        dopoSoccer.getProfile(LEFT).addPoints(1000);
        int price = dopoSoccer.getUpgradePrice(LEFT, Stats.SPEED_STAT);

        dopoSoccer.upgradeStats(LEFT, Stats.SPEED_STAT);

        assertEquals(2, dopoSoccer.getPlayerStats(LEFT)[Stats.SPEED_STAT]);
        assertEquals(1000 - price, dopoSoccer.getPlayerPoints(LEFT));
        assertEquals(price * 2, dopoSoccer.getUpgradePrice(LEFT, Stats.SPEED_STAT));
    }

    @Test
    public void shouldNotUpgradeStatWithoutEnoughPoints() {
        try {
            dopoSoccer.upgradeStats(LEFT, Stats.KICK_STAT);
            fail("Debio lanzar excepcion por puntos insuficientes");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.NOT_ENOUGH_POINTS, e.getMessage());
        }
        assertEquals(Stats.MIN_LEVEL, dopoSoccer.getPlayerStats(LEFT)[Stats.KICK_STAT]);
        assertEquals(0, dopoSoccer.getPlayerPoints(LEFT));
    }

    // ==================== saveAs ====================

    @Test
    public void shouldSaveGameInDopoFile() throws DopoSoccerException {
        startHumanGame();
        File file = tempDir.resolve("partida.dopo").toFile();
        DopoSoccer.saveAs(file, dopoSoccer);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void shouldNotSaveGameWithInvalidExtension() {
        File file = tempDir.resolve("partida.txt").toFile();
        try {
            DopoSoccer.saveAs(file, dopoSoccer);
            fail("Debio lanzar excepcion por extension invalida");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_DOPO_EXTENSION, e.getMessage());
        }
        assertFalse(file.exists());
    }

    // ==================== open ====================

    @Test
    public void shouldOpenSavedGameWithSameState() throws DopoSoccerException {
        dopoSoccer.getProfile(LEFT).addPoints(700);
        startHumanGame();
        play(1);
        File file = tempDir.resolve("partida.dopo").toFile();
        DopoSoccer.saveAs(file, dopoSoccer);

        DopoSoccer opened = DopoSoccer.open(file);

        assertNotSame(dopoSoccer, opened);
        assertTrue(opened.hasMatch());
        assertEquals(700, opened.getPlayerPoints(LEFT));
        assertEquals(dopoSoccer.getTime(), opened.getTime());
        assertEquals(dopoSoccer.getPlayerX(LEFT), opened.getPlayerX(LEFT));
        assertEquals(Camerun.NAME, opened.getCharacter(RIGHT));
    }

    @Test
    public void shouldNotOpenInexistentOrInvalidFile() {
        try {
            DopoSoccer.open(tempDir.resolve("noExiste.dopo").toFile());
            fail("Debio lanzar excepcion por archivo inexistente");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.FILE_NOT_FOUND, e.getMessage());
        }

        try {
            DopoSoccer.open(tempDir.resolve("partida.dat").toFile());
            fail("Debio lanzar excepcion por extension invalida");
        } catch (DopoSoccerException e) {
            assertEquals(DopoSoccerException.INVALID_DOPO_EXTENSION, e.getMessage());
        }
    }
}

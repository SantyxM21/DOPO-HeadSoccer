package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class StatsTest {
    private Stats stats;

    @BeforeEach
    public void setUp() {
        stats = new Stats();
    }

    @Test
    public void shouldStartAllStatsAtLevelOne() {
        assertEquals(1, stats.getLevel(Stats.SPEED_STAT));
        assertEquals(1, stats.getLevel(Stats.JUMP_STAT));
        assertEquals(1, stats.getLevel(Stats.KICK_STAT));
        assertEquals(1, stats.getLevel(Stats.HEALTH_STAT));
    }

    @Test
    public void shouldUpgradeStatAndReturnItsPrice() throws DopoSoccerException {
        int price = stats.upgradeStat(Stats.KICK_STAT, 100000);
        assertEquals(500, price);
        assertEquals(2, stats.getLevel(Stats.KICK_STAT));
    }

    @Test
    public void shouldNotUpgradeWithoutEnoughPoints() {
        DopoSoccerException e = assertThrows(DopoSoccerException.class, () -> stats.upgradeStat(Stats.KICK_STAT, 100));
        assertEquals(DopoSoccerException.NOT_ENOUGH_POINTS, e.getMessage());
        assertEquals(1, stats.getLevel(Stats.KICK_STAT));
    }

    @Test
    public void shouldThrowExceptionForInvalidStatIndex() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> stats.getLevel(Stats.STATS_NAMES.length));
        assertEquals(DopoSoccerException.INVALID_STAT, e.getMessage());
        assertThrows(IllegalArgumentException.class, () -> stats.getLevel(-1));
    }

    @Test
    public void shouldDoubleUpgradePriceAfterEachLevel() throws DopoSoccerException {
        assertEquals(500, stats.getUpgradePrice(Stats.KICK_STAT));
        stats.upgradeStat(Stats.KICK_STAT, 100000);
        assertEquals(1000, stats.getUpgradePrice(Stats.KICK_STAT));
        stats.upgradeStat(Stats.KICK_STAT, 100000);
        assertEquals(2000, stats.getUpgradePrice(Stats.KICK_STAT));
        assertEquals(500, stats.getUpgradePrice(Stats.SPEED_STAT));
    }

    @Test
    public void shouldReturnZeroPriceAtMaxLevel() throws DopoSoccerException {
        for (int i = Stats.MIN_LEVEL; i < Stats.MAX_LEVEL; i++) stats.upgradeStat(Stats.JUMP_STAT, Integer.MAX_VALUE);
        assertEquals(Stats.MAX_LEVEL, stats.getLevel(Stats.JUMP_STAT));
        assertEquals(0, stats.getUpgradePrice(Stats.JUMP_STAT));
        DopoSoccerException e = assertThrows(DopoSoccerException.class, () -> stats.upgradeStat(Stats.JUMP_STAT, Integer.MAX_VALUE));
        assertEquals(DopoSoccerException.MAX_LEVEL_REACHED, e.getMessage());
    }

    @Test
    public void shouldNotUpgradeInexistentStat() {
        assertThrows(IllegalArgumentException.class, () -> stats.upgradeStat(Stats.STATS_NAMES.length, 100000));
    }
}

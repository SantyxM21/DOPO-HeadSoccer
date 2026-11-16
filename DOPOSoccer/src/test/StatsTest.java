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
        assertEquals(1, stats.getLevel("speed"));
        assertEquals(1, stats.getLevel("jump"));
        assertEquals(1, stats.getLevel("kick"));
        assertEquals(1, stats.getLevel("survival"));
    }

    @Test
    public void shouldUpgradeStatAndReturnItsPrice() {
        int price = stats.upgradeStat("Patada", 100000);
        assertEquals(500, price);
        assertEquals(2, stats.getLevel("kick"));
    }

    @Test
    public void shouldNotUpgradeWithoutEnoughPoints() {
        assertEquals(0, stats.upgradeStat("Patada", 100));
        assertEquals(1, stats.getLevel("kick"));
    }

    @Test
    public void shouldNotUpgradeInexistentStat() {
        assertEquals(0, stats.upgradeStat("Inexistente", 100000));
    }
}

package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class ProfileTest {
     private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile("1");
    }

    @Test
    public void shouldConstructProfileWithName() {
        assertEquals("Profile #1", profile.getName());
        assertEquals(1, profile.getStatLevel(Stats.KICK_STAT));
    }

    @Test
    public void shouldAddPoints() {
        int initialPoints = profile.getPoints();
        profile.addPoints(100);
        assertEquals(initialPoints + 100, profile.getPoints());
    }

    @Test
    public void shouldDiscountPointsWhenUpgradingStat() throws DopoSoccerException {
        int initialPoints = profile.getPoints();
        profile.upgradeStat(Stats.KICK_STAT);
        assertEquals(initialPoints - 500, profile.getPoints());
        assertEquals(2, profile.getStatLevel(Stats.KICK_STAT));
    }
}

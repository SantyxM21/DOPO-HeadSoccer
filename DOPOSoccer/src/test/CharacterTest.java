package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domain.*;

public class CharacterTest {
    @Test
    public void shouldCreateCoreaWithBlueAura() {
        Corea corea = new Corea();
        assertEquals("Corea", corea.getName());
        assertEquals("Aura Azul", corea.getPower().getName());
        assertEquals(40, corea.getPower().getShootDamage(), 0.001);
        assertEquals(1.5, corea.getPower().getDurationEffect(), 0.001);
    }

    @Test
    public void shouldCreateCamerunWithLightning() {
        Camerun camerun = new Camerun();
        assertEquals("Camerun", camerun.getName());
        assertEquals("Rayo", camerun.getPower().getName());
        assertEquals(60, camerun.getPower().getShootDamage(), 0.001);
    }

    @Test
    public void shouldCreateNigeriaWithTornado() {
        Nigeria nigeria = new Nigeria();
        assertEquals("Nigeria", nigeria.getName());
        assertEquals("Tornado", nigeria.getPower().getName());
        assertEquals(40, nigeria.getPower().getShootDamage(), 0.001);
    }

    @Test
    public void shouldCreateUSAWithIlusion() {
        USA usa = new USA();
        assertEquals("USA", usa.getName());
        assertEquals("Ilusión", usa.getPower().getName());
        assertEquals(30, usa.getPower().getShootDamage(), 0.001);
    }
}

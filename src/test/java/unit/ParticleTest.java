package unit;

import static org.junit.jupiter.api.Assertions.*;

import org.acme.Particle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParticleTest {
    private Particle particle;

    @BeforeEach
    void setUp() {
        particle = new Particle(1.0, 2.0, 3.0, 4.0, 5.0);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1.0, particle.getX());
        assertEquals(2.0, particle.getY());
        assertEquals(3.0, particle.getVx());
        assertEquals(4.0, particle.getVy());
        assertEquals(5.0, particle.getMass());
    }

    @Test
    void testResetForce() {
        particle.addForce(10, 20);
        particle.resetForce();
        particle.update(1);  // Ensure no residual force affects motion
        assertEquals(3.0, particle.getVx());
        assertEquals(4.0, particle.getVy());
    }

    @Test
    void testAddForce() {
        particle.addForce(10, 5);
        particle.update(1);
        assertEquals(3.0 + (10.0 / 5.0), particle.getVx(), 0.001);
        assertEquals(4.0 + (5.0 / 5.0), particle.getVy(), 0.001);
    }

    @Test
    void testUpdate() {
        particle.addForce(10, 5);
        particle.update(2);
        assertEquals(1.0 + (3.0 + 2 * (10.0 / 5.0)) * 2, particle.getX(), 0.001);
        assertEquals(2.0 + (4.0 + 2 * (5.0 / 5.0)) * 2, particle.getY(), 0.001);
    }

    @Test
    void testUpdateHandlesNaN() {
        particle.addForce(Double.NaN, Double.NaN);
        particle.update(1);
        assertEquals(4.0, particle.getX());
        assertEquals(6.0, particle.getY());
    }

    @Test
    void testUpdateHandlesInfinity() {
        particle.addForce(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        particle.update(1);
        assertEquals(4.0, particle.getX());
        assertEquals(6.0, particle.getY());
    }

    @Test
    void testToString() {
        assertEquals("Particle at (1.00, 2.00) with velocity (3.00, 4.00)", particle.toString());
    }
}

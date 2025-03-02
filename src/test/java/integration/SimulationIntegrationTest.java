package integration;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.SimulationService;
import org.acme.SimulationResource;
import org.acme.Particle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulationIntegrationTest {

    @Inject
    SimulationService simulationService;

    @Inject
    SimulationResource simulationResource;

    @BeforeEach
    void setUp() {
        simulationService.getParticles().clear(); // Clear existing particles before each test
        simulationService.addParticle(new Particle(0, 0, 0, 0, 5e15)); // Initialize with central mass
    }

    @Test
    void shouldAddParticleViaResource() {
        Particle particle = new Particle(1.0, 2.0, 3.0, 4.0, 5.0);
        simulationResource.addParticle(particle);
        assertEquals(2, simulationService.getParticles().size());
    }

    @Test
    void shouldGetParticlesViaResource() {
        assertEquals(1, simulationResource.getParticles().size());
    }

    @Test
    void shouldUpdateSimulationViaResource() {
        Particle particle = new Particle(1.0, 2.0, 3.0, 4.0, 5.0);
        simulationResource.addParticle(particle);
        simulationResource.updateSimulation();
        // Add assertions to verify the state of particles after update
        assertNotNull(simulationService.getParticles().get(1));
    }

    @Test
    void shouldUpdateSunPositionViaResource() {
        Particle newSun = new Particle(0, 0, 0, 0, 1e16);
        simulationResource.updateSunPosition(newSun);
        assertEquals(1e16, simulationService.getParticles().get(0).getMass());
    }
}
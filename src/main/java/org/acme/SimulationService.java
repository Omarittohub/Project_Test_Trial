package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulationService {
    private static final double G = 6.67430e-11; // Gravitational constant
    private static final double TIME_STEP = 0.05; // Smaller time step for stability
    private List<Particle> particles = new ArrayList<>();

    public SimulationService() {
        initializeCentralMass(); // Adds a massive object at the center
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    private void initializeCentralMass() {
        // Large "Sun-like" mass in the center to keep particles orbiting
        particles.add(new Particle(0, 0, 0, 0, 5e12));
    }

    public void updateSimulation() {
        for (Particle p : particles) {
            p.resetForce();
        }

        // Compute gravitational forces
        for (int i = 0; i < particles.size(); i++) {
            for (int j = i + 1; j < particles.size(); j++) {
                applyGravity(particles.get(i), particles.get(j));
            }
        }

        // Update positions
        for (Particle p : particles) {
            p.update(TIME_STEP);
        }
    }

    private void applyGravity(Particle p1, Particle p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double distanceSquared = dx * dx + dy * dy;

        if (distanceSquared < 1e-4) return;

        double distance = Math.sqrt(distanceSquared);
        double force = (G * p1.getMass() * p2.getMass()) / distanceSquared;

        // Acceleration components
        double fx = force * (dx / distance);
        double fy = force * (dy / distance);

        p1.addForce(fx, fy);
        p2.addForce(-fx, -fy);
    }

}

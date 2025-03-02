package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulationService {
    private static final double G = 6.67430e-11; 
    private static final double TIME_STEP = 0.05;
    private List<Particle> particles = new ArrayList<>();

    public SimulationService() {
        initializeCentralMass();
    }

    public void addParticle(Particle p) {
        particles.add(p);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    private void initializeCentralMass() {
        if (particles.isEmpty()) {
            particles.add(new Particle(0, 0, 0, 0, 5e15));
        } else {
            Particle sun = particles.get(0);
            sun.setX(0);
            sun.setY(0);
            sun.setVx(0);
            sun.setVy(0);
            sun.setMass(5e15);
        }
    }


    public void updateSimulation() {
        for (Particle p : particles) {
            p.resetForce();
        }

        for (int i = 0; i < particles.size(); i++) {
            for (int j = i + 1; j < particles.size(); j++) {
                applyGravity(particles.get(i), particles.get(j));
            }
        }

        Particle sun = particles.get(0);
        for (int i = 1; i < particles.size(); i++) {
            Particle p = particles.get(i);
            double dx = p.getX() - sun.getX();
            double dy = p.getY() - sun.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 20 && sun.getMass() > 1e15) {
                p.setX(sun.getX());
                p.setY(sun.getY());
                p.setVx(0);
                p.setVy(0);
            } else {
                p.update(TIME_STEP);
            }
        }
    }

    private void applyGravity(Particle p1, Particle p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double distanceSquared = dx * dx + dy * dy + 1e-4;
        double distance = Math.sqrt(distanceSquared);
        double force = (G * p1.getMass() * p2.getMass()) / distanceSquared;

        double fx = force * (dx / distance);
        double fy = force * (dy / distance);

        p1.addForce(fx, fy);
        p2.addForce(-fx, -fy);
    }


    public void updateSunPosition(Particle newSun) {
        Particle sun = particles.get(0);
        sun.setX(newSun.getX());
        sun.setY(newSun.getY());
        sun.setMass(newSun.getMass());
    }





}

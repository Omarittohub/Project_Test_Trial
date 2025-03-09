package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

/**
 * Service de simulation pour un système N-corps.
 * Ce service gère l'ajout de particules, leur mise à jour et l'application de la gravité.
 */
@ApplicationScoped
public class SimulationService {

    /** Constante de gravitation universelle. */
    private static final double G = 6.67430e-11;

    /** Pas de temps pour la mise à jour de la simulation. */
    private static final double TIME_STEP = 0.05;

    /** Liste des particules de la simulation. */
    private List<Particle> particles = new ArrayList<>();

    /**
     * Constructeur qui initialise la masse centrale.
     */
    public SimulationService() {
        initializeCentralMass();
    }

    /**
     * Ajoute une nouvelle particule à la simulation.
     *
     * @param p La particule à ajouter
     */
    public void addParticle(Particle p) {
        particles.add(p);
    }

    /**
     * Retourne la liste des particules de la simulation.
     *
     * @return Liste des particules
     */
    public List<Particle> getParticles() {
        return particles;
    }

    /**
     * Initialise la masse centrale (le "soleil" de la simulation).
     * Si aucune particule n'est présente, une particule centrale est ajoutée.
     */
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

    /**
     * Met à jour la simulation en recalculant les forces et les positions des particules.
     */
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

    /**
     * Applique la force gravitationnelle entre deux particules.
     *
     * @param p1 Première particule
     * @param p2 Deuxième particule
     */
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

    /**
     * Met à jour la position de la masse centrale (le soleil).
     *
     * @param newSun Nouvelle position et masse du soleil
     */
    public void updateSunPosition(Particle newSun) {
        Particle sun = particles.get(0);
        sun.setX(newSun.getX());
        sun.setY(newSun.getY());
        sun.setMass(newSun.getMass());
    }
}


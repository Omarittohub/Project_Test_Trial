package org.acme;

public class Particle {
    private double x, y;
    private double vx, vy;
    private double ax, ay;
    private double mass;

    public Particle() {} // Needed for JSON serialization

    public Particle(double x, double y, double vx, double vy, double mass) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = 0;
        this.ay = 0;
        this.mass = mass;
    }

    public void resetForce() {
        ax = 0;
        ay = 0;
    }

    public void addForce(double fx, double fy) {
        ax += fx / mass;
        ay += fy / mass;
    }

    public void update(double dt) {
        if (Double.isNaN(ax) || Double.isNaN(ay)) {
            ax = 0;
            ay = 0;
        }
        if (Double.isInfinite(ax) || Double.isInfinite(ay)) {
            ax = 0;
            ay = 0;
        }

        vx += ax * dt;
        vy += ay * dt;
        x += vx * dt;
        y += vy * dt;

        // 🚀 Prevent Invalid Positions
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isInfinite(x) || Double.isInfinite(y)) {
            x = 0;
            y = 0;
            vx = 0;
            vy = 0;
        }
    }


    // Getters and Setters (Needed for JSON mapping)
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getMass() { return mass; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
    public void setMass(double mass) { this.mass = mass; }

    @Override
    public String toString() {
        return String.format("Particle at (%.2f, %.2f) with velocity (%.2f, %.2f)", x, y, vx, vy);
    }
}

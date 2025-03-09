package org.acme;

/**
 * Représente une particule dans une simulation N-corps.
 * Une particule a une position, une vitesse, une accélération et une masse.
 * Elle peut être influencée par des forces externes et évolue dans le temps selon ces forces.
 */
public class Particle {

    /**
     * Coordonnées x et y de la particule.
     */
    private double x, y;

    /**
     * Composantes de la vitesse en x et y.
     */
    private double vx, vy;

    /**
     * Composantes de l'accélération en x et y.
     */
    private double ax, ay;

    /**
     * Masse de la particule.
     */
    private double mass;

    /**
     * Constructeur par défaut requis pour la sérialisation JSON.
     */
    public Particle() {
    }

    /**
     * Initialise une particule avec des valeurs spécifiques.
     *
     * @param x    Position initiale en x
     * @param y    Position initiale en y
     * @param vx   Vitesse initiale en x
     * @param vy   Vitesse initiale en y
     * @param mass Masse de la particule
     */
    public Particle(double x, double y, double vx, double vy, double mass) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.ax = 0;
        this.ay = 0;
        this.mass = mass;
    }

    /**
     * Réinitialise les forces appliquées sur la particule.
     */
    public void resetForce() {
        ax = 0;
        ay = 0;
    }

    /**
     * Ajoute une force à la particule en fonction de sa masse.
     *
     * @param fx Force appliquée en x
     * @param fy Force appliquée en y
     */
    public void addForce(double fx, double fy) {
        if (Double.isNaN(fx) || Double.isNaN(fy) || Double.isInfinite(fx) || Double.isInfinite(fy)) {
            return;
        }
        ax += fx / mass;
        ay += fy / mass;
    }

    /**
     * Met à jour la position et la vitesse de la particule en fonction du temps écoulé.
     *
     * @param dt Intervalle de temps (delta t)
     */
    public void update(double dt) {
        if (Double.isNaN(ax) || Double.isNaN(ay)) {
            ax = 0;
            ay = 0;
            vx = 0;
            vy = 0;
        }
        if (Double.isInfinite(ax) || Double.isInfinite(ay)) {
            ax = 0;
            ay = 0;
            vx = 0;
            vy = 0;
        }

        vx += ax * dt;
        vy += ay * dt;
        x += vx * dt;
        y += vy * dt;

        if (Double.isNaN(x) || Double.isNaN(y) || Double.isInfinite(x) || Double.isInfinite(y)) {
            x = 0;
            y = 0;
            vx = 0;
            vy = 0;
        }
    }

    /**
     * @return La position en x de la particule
     */
    public double getX() {
        return x;
    }

    /**
     * @return La position en y de la particule
     */
    public double getY() {
        return y;
    }

    /**
     * @return La vitesse en x de la particule
     */
    public double getVx() {
        return vx;
    }

    /**
     * @return La vitesse en y de la particule
     */
    public double getVy() {
        return vy;
    }

    /**
     * @return La masse de la particule
     */
    public double getMass() {
        return mass;
    }

    /**
     * Définit la position en x de la particule
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Définit la position en y de la particule
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Définit la vitesse en x de la particule
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Définit la vitesse en y de la particule
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * Définit la masse de la particule
     */
    public void setMass(double mass) {
        this.mass = mass;
    }
}
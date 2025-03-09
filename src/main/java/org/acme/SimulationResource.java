package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
/**
 * Ressource REST pour gérer la simulation N-corps.
 * Fournit des points de terminaison pour ajouter des particules,
 * récupérer la liste des particules et mettre à jour la simulation.
 */
@Path("/simulation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulationResource {

    /** Service de simulation injecté pour gérer les particules et leur évolution. */
    @Inject
    SimulationService simulationService;

    /**
     * Ajoute une particule à la simulation.
     *
     * @param particle La particule à ajouter
     * @return Un message confirmant l'ajout
     */
    @POST
    @Path("/add")
    public String addParticle(Particle particle) {
        simulationService.addParticle(particle);
        return "Particle added successfully!";
    }

    /**
     * Récupère la liste des particules actuellement présentes dans la simulation.
     *
     * @return Liste immuable des particules
     */
    @GET
    @Path("/particles")
    public List<Particle> getParticles() {
        return List.copyOf(simulationService.getParticles());
    }

    /**
     * Met à jour la simulation en recalculant les positions et forces des particules.
     *
     * @return Un message confirmant la mise à jour
     */
    @POST
    @Path("/update")
    public String updateSimulation() {
        simulationService.updateSimulation();
        return "Simulation updated!";
    }

    /**
     * Met à jour la position et la masse de la masse centrale (le soleil).
     *
     * @param newSunPosition Nouvelle position et masse du soleil
     */
    @POST
    @Path("/update-sun")
    public void updateSunPosition(Particle newSunPosition) {
        simulationService.updateSunPosition(newSunPosition);
    }
}


package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/simulation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulationResource {

    @Inject
    SimulationService simulationService;

    @POST
    @Path("/add")
    public String addParticle(Particle particle) {
        simulationService.addParticle(particle);
        return "Particle added successfully!";
    }

    @GET
    @Path("/particles")
    public List<Particle> getParticles() {
        return simulationService.getParticles();
    }

    @POST
    @Path("/update")
    public String updateSimulation() {
        simulationService.updateSimulation();
        return "Simulation updated!";
    }

    @POST
    @Path("/update-sun")
    public void updateSunPosition(Particle newSunPosition) {
        simulationService.updateSunPosition(newSunPosition);
    }
}

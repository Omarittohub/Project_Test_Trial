package api;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.Particle;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class SimulationResourceApiTest {

    @Test
    public void testAddParticleEndpoint() {
        Particle particle = new Particle(1.0, 2.0, 3.0, 4.0, 5.0);

        given()
            .contentType("application/json")
            .body(particle)
            .when().post("/simulation/add")
            .then()
            .statusCode(200)
            .body(is("Particle added successfully!"));
    }

    @Test
    public void testGetParticlesEndpoint() {
        given()
            .when().get("/simulation/particles")
            .then()
            .statusCode(200)
            .body("$", hasSize(1)); // Assuming one particle is added in the setup
    }

    @Test
    public void testUpdateSimulationEndpoint() {
        given()
            .when().post("/simulation/update")
            .then()
            .statusCode(200)
            .body(is("Simulation updated!"));
    }

    @Test
    public void testUpdateSunPositionEndpoint() {
        Particle newSun = new Particle(0, 0, 0, 0, 1e16);

        given()
            .contentType("application/json")
            .body(newSun)
            .when().post("/simulation/update-sun")
            .then()
            .statusCode(204); // No content
    }
}
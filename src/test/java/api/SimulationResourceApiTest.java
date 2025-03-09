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
    public void testGetParticlesEndpoint() {
        given()
            .when().get("/simulation/particles")
            .then()
            .statusCode(200)
            .body("$", hasSize(1)); // Assuming one particle is added in the setup
    }

}
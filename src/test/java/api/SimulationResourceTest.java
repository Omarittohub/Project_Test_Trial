package api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.acme.Particle ;

@QuarkusTest
public class SimulationResourceTest {
    @Test
    void testAddParticle() {
        given()
                .contentType(ContentType.JSON)
                .body(new Particle(10, 10, 2, 2, 5))
                .when()
                .post("/simulation/add")
                .then()
                .statusCode(200)
                .body(equalTo("Particle added successfully!"));
    }

    @Test
    void testAddMultipleParticles() {
        given().contentType(ContentType.JSON).body(new Particle(5, 5, 1, 1, 3)).post("/simulation/add");
        given().contentType(ContentType.JSON).body(new Particle(-5, -5, -1, -1, 4)).post("/simulation/add");

        given()
                .when().get("/simulation/particles")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(3)); // Soleil + 2 particules
    }

    @Test
    void testGetParticles() {
        given()
                .when().get("/simulation/particles")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testUpdateSimulation() {
        given()
                .when().post("/simulation/update")
                .then()
                .statusCode(200)
                .body(equalTo("Simulation updated!"));
    }

    @Test
    void testUpdateSunPosition() {
        given()
                .contentType(ContentType.JSON)
                .body(new Particle(50, 50, 0, 0, 1e16))
                .when()
                .post("/simulation/update-sun")
                .then()
                .statusCode(204);
    }

    @Test
    void testUpdateSunAndCheckParticles() {
        given()
                .contentType(ContentType.JSON)
                .body(new Particle(30, 30, 0, 0, 1e16))
                .when()
                .post("/simulation/update-sun");

        given()
                .when().get("/simulation/particles")
                .then()
                .statusCode(200)
                .body("[0].x", equalTo(30.0F))
                .body("[0].y", equalTo(30.0F));
    }

    @Test
    void testEmptyBodyHandling() {
        given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post("/simulation/add")
                .then()
                .statusCode(400); // Mauvaise requête attendue
    }


    @Test
    void testParticlePersistenceAfterUpdate() {
        given().contentType(ContentType.JSON).body(new Particle(15, 15, 2, 2, 10)).post("/simulation/add");

        given().when().post("/simulation/update");

        given()
                .when().get("/simulation/particles")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(2));
    }

    @Test
    void testZeroMassParticleRejection() {
        given()
                .contentType(ContentType.JSON)
                .body(new Particle(20, 20, 1, 1, 0))
                .when()
                .post("/simulation/add")
                .then()
                .statusCode(400);
    }
}

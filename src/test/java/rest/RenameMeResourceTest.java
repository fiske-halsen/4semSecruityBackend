package rest;

import entities.Car;
import entities.RentalOrder;
import entities.Role;
import entities.User;
import facades.OrderFacade;
import facades.UserFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
@Disabled
public class RenameMeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
   

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static User u1, u2, admin, both;
    private static Car c1, c2;
    private static RentalOrder o;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    //Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
      @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createNativeQuery("DELETE FROM RENTALORDER").executeUpdate();
            em.createNativeQuery("DELETE FROM CAR").executeUpdate();
            em.createNativeQuery("DELETE FROM user_roles").executeUpdate();
            em.createNativeQuery("DELETE FROM roles").executeUpdate();
            em.createNativeQuery("DELETE FROM users").executeUpdate();
            
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            u1 = new User("user", "test");
            u2 = new User("user2", "test");
            u1.addRole(userRole);
            u2.addRole(userRole);
            admin = new User("admin", "test");
            admin.addRole(adminRole);
            both = new User("user_admin", "test");
            both.addRole(userRole);
            both.addRole(adminRole);
            c1 = new Car("BMW", "M5", 2020, 150);
            c2 = new Car("Hyundai", "i20", 2020, 125);
            o = new RentalOrder(10, 1500);
            u1.addRentalOrder(o);
            o.addCar(c1);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(admin);
            em.persist(both);
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/order").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/order/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello anonymous"));
    }

    
    @Test
  
    public void testParrallel() throws Exception {
        given()
                .contentType("application/json")
                .get("/order/getreservations").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("peopleName", equalTo("Luke Skywalker"))
                .body("planetName", equalTo("Yavin IV"))
                .body("speciesName", equalTo("Ewok"))
                .body("starshipName", equalTo("Star Destroyer"))
                .body("vehicleName", equalTo("Sand Crawler"));

 
    }

    @Test
    
    public void testGetCars() throws Exception {
        given()
                .contentType("application/json")
                .get("/order/getcars").then()            
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("peopleName", equalTo("Luke Skywalker"));
               
    }
}

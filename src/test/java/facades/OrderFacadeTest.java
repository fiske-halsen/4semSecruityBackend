package facades;

import dto.CarDTO;
import dto.CarsDTO;
import dto.CreateRentalDTO;
import dto.RentalDTO;
import dto.RentalsDTO;
import entities.Car;
import utils.EMF_Creator;
import entities.RentalOrder;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class OrderFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade userFacade;
    private static OrderFacade orderFacade;
    private static User u1, u2, admin, both;
    private static Car c1, c2;
    private static RentalOrder o;
   
    public OrderFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       userFacade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
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
    @AfterEach
    public void tearDown() {
 // Remove any data after each test was run
    }

   
    @Test
    public void testVerifyUser() throws AuthenticationException {
        User user = userFacade.getVeryfiedUser("admin", "test");
        assertEquals("admin", admin.getUserName());
    }
    
    @Test
    public void testMakeReservations() throws AuthenticationException {
        RentalDTO rentalDTO = orderFacade.makeReservation(new CreateRentalDTO("user2", 8, "Hyundai", "i20", 2020, 125));
        double expRentalPrice = 1000;
        assertEquals(expRentalPrice, rentalDTO.totalRentalPrice, "Expects the total rentalprice to be 1000");
    }
    
    @Test
    public void testGetCars() {
        CarsDTO cars = orderFacade.getCars();
        assertEquals(2, cars.cars.size(), "Expects ");
        assertThat(cars.cars, containsInAnyOrder(new CarDTO(c1)));
    }
    
     @Test
    public void testGetReservations() {
        RentalsDTO rentals = orderFacade.getReservations();
        assertEquals(1, rentals.rentalOrders.size(), "Expects ");
        assertThat(rentals.rentalOrders, containsInAnyOrder(new RentalDTO(o)));
    }
    
    
    

}

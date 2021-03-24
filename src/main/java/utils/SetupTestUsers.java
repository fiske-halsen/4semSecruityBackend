package utils;

import dto.OrderDTO;
import entities.FullOrder;
import entities.FullOrder;
import entities.Product;
import entities.Role;
import entities.User;
import facades.OrderFacade;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OrderFacade FACADE = OrderFacade.getFacadeExample(EMF);

    public static void setUpUsers() {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
        // CHANGE the three passwords below, before you uncomment and execute the code below
        // Also, either delete this file, when users are created or rename and add to .gitignore
        // Whatever you do DO NOT COMMIT and PUSH with the real passwords
        User user = new User("user", "testuser");
        
        User admin = new User("admin", "testadmin");
        User both = new User("user_admin", "testuseradmin");
        Product p = new Product("BMW", "M5", 2020, 20.99);
        FullOrder o = new FullOrder();

        Product p2 = new Product("saddad", "M5sadsd", 202220, 20.9231329);
        Product p3 = new Product("Mercedes", "AMG", 202220, 20.9231329);
        FullOrder o2 = new FullOrder();

     
        

        if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test")) {
            throw new UnsupportedOperationException("You have not changed the passwords");
        }

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);

        em.getTransaction().commit();
        user.addOrder(o2);
        o2.addProduct(p2);
        o2.addProduct(p3);
        FACADE.makeOrder(new OrderDTO(o2));
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");

    }
    
    
    public static void main(String[] args) {
        setUpUsers();
        
    }

}

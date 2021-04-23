package facades;

import dto.rentalDTO;
import dto.carDTO;
import dto.carsDTO;
import entities.RentalOrder;
import entities.Car;
import entities.RenameMe;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class OrderFacade {

    private static OrderFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private OrderFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static OrderFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new OrderFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
        
    }
    
     public rentalDTO makeOrder(rentalDTO orderDTO){
        EntityManager em = emf.createEntityManager();
        User user;
        RentalOrder order;
        
        try{
            em.getTransaction().begin();
             user = em.find(User.class, orderDTO.userName);
            order = new RentalOrder();
            
            user.addRentalOrder(order);
            
            for (carDTO product : orderDTO.products) {
                order.addCar(new Car(product.brand, product.model, product.year, product.price));
            }
            em.merge(user);
            
            em.getTransaction().commit();
            
        }finally{  
            em.close();
        }
        return new rentalDTO(order);
    }
     
     public carsDTO getProducts(){
        EntityManager em = emf.createEntityManager();
        List<Car> products;
        try{
             products = em.createQuery("SELECT p FROM Product p").getResultList();
        }finally{  
            em.close();
        }
        return new carsDTO(products);
    }
}

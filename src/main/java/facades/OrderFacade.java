package facades;

import dto.OrderDTO;
import dto.ProductDTO;
import entities.FullOrder;
import entities.Product;
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
    
     public OrderDTO makeOrder(OrderDTO orderDTO){
        EntityManager em = emf.createEntityManager();
        User user;
        FullOrder order;
        
        try{
            em.getTransaction().begin();
             user = em.find(User.class, orderDTO.userName);
            order = new FullOrder();
            
            user.addOrder(order);
            
            for (ProductDTO product : orderDTO.products) {
                order.addProduct(new Product(product.brand, product.model, product.year, product.price));
            }
            em.merge(user);
            
            em.getTransaction().commit();
            
        }finally{  
            em.close();
        }
        return new OrderDTO(order);
    }
    
}

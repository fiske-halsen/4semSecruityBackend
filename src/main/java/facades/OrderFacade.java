package facades;

import dto.RentalDTO;
import dto.CarDTO;
import dto.CarsDTO;
import dto.CreateRentalDTO;
import dto.RentalsDTO;
import entities.RentalOrder;
import entities.Car;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;

public class OrderFacade {

    private static OrderFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private OrderFacade() {
    }

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

    public RentalDTO makeReservation(CreateRentalDTO createRentalDTO) {
        EntityManager em = emf.createEntityManager();
        Car car;
        User user;
        RentalOrder order;
        double totalRentalPrice;
        try {
            em.getTransaction().begin();
            user = em.find(User.class, createRentalDTO.userName);

            Query query = em.createQuery("SELECT c FROM Car c WHERE c.model = :model ");
            query.setParameter("model", createRentalDTO.model);
            car = (Car) query.getSingleResult();

            totalRentalPrice = createRentalDTO.rentalDays * car.getPricePerDay();

            order = new RentalOrder(createRentalDTO.rentalDays, totalRentalPrice);
            order.addCar(car);
            user.addRentalOrder(order);

            em.merge(user);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return new RentalDTO(order);
    }

    public CarsDTO getCars() {
        EntityManager em = emf.createEntityManager();
        List<Car> cars;
        try {
            cars = em.createQuery("SELECT c FROM Car c").getResultList();
        } finally {
            em.close();
        }
        return new CarsDTO(cars);
    }

    public RentalsDTO getReservations() {
        EntityManager em = emf.createEntityManager();
        List<RentalOrder> rentalOrders;
        try {
            rentalOrders = em.createQuery("SELECT r FROM RentalOrder r").getResultList();
        } finally {
            em.close();
        }
        return new RentalsDTO(rentalOrders);
    }
    public RentalDTO deleteReservation(long id){
        EntityManager em = emf.createEntityManager();
        RentalOrder rentalOrder;
        try {
            rentalOrder = em.find(RentalOrder.class, id);
            em.getTransaction().begin();
            em.remove(rentalOrder);
            em.getTransaction().commit();
            return new RentalDTO(rentalOrder);
        } finally {
            em.close();
        }
        
    }
    
    public RentalDTO editReservation(RentalDTO rentalDTO){
        EntityManager em = emf.createEntityManager();
        RentalOrder rentalOrder;
        Car car;
        try {
            rentalOrder = em.find(RentalOrder.class, rentalDTO.id);
            rentalOrder.setRentalDays(rentalDTO.rentalDays);    
            Query query = em.createQuery("SELECT c FROM Car c WHERE c.model = :model ");
            query.setParameter("model", rentalDTO.model);
            car = (Car) query.getSingleResult();
            rentalOrder.addCar(car);
            em.getTransaction().begin();
            em.merge(rentalOrder);         
            em.getTransaction().commit();
            return new RentalDTO(rentalOrder);
        } finally {
            em.close();
        }
        
        
    }
    

}

package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class RentalOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private int rentalDays;
    @Temporal(TemporalType.DATE)
    private Date rentalDate;
    private double totalRentalPrice;
  
   
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Car car;

    public RentalOrder() {
    }
    
    public RentalOrder(int rentalDays, double totalRentalPrice) {
        this.rentalDays = rentalDays;
        this.totalRentalPrice = totalRentalPrice;
        this.rentalDate = new Date();
    
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getTotalRentalPrice() {
        return totalRentalPrice;
    }

    public void calcTotalRentalPrice(double totalRentalPrice) {
        this.totalRentalPrice = totalRentalPrice;
    }
   
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void addCar(Car car) {
        this.car = car;
    }

   

 
    
}

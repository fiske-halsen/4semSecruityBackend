
package dto;

import entities.RentalOrder;
import java.util.Date;


public class CreateRentalDTO {
    
    public String userName;
    public int rentalDays;
    public String brand;
    public String model;
    public int year;
    public double pricePerDay;
 
   
    
    public CreateRentalDTO(RentalOrder order) {
        this.userName = order.getUser().getUserName();
        this.rentalDays = order.getRentalDays();
        this.brand = order.getCar().getBrand();
        this.model = order.getCar().getModel();
        this.year = order.getCar().getYear();
        this.pricePerDay = order.getCar().getPricePerDay();

    }
    
    
}

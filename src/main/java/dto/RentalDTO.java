
package dto;

import entities.RentalOrder;
import java.util.Date;

public class RentalDTO {
    
    public String userName;
    public int rentalDays;
    public Date rentalDate;
    public double totalRentalPrice;
    public String brand;
    public String model;
    public int year;
    public double pricePerDay;
 
   
    
    public RentalDTO(RentalOrder order) {
        this.userName = order.getUser().getUserName();
        this.rentalDate = order.getRentalDate();
        this.rentalDays = order.getRentalDays();
        this.totalRentalPrice = order.getTotalRentalPrice();
        this.brand = order.getCar().getBrand();
        this.model = order.getCar().getModel();
        this.year = order.getCar().getYear();
        this.pricePerDay = order.getCar().getPricePerDay();

    }
    
}


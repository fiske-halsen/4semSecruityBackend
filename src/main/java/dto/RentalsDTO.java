
package dto;

import entities.Car;
import entities.RentalOrder;
import java.util.ArrayList;
import java.util.List;


public class RentalsDTO {
    
    public List<RentalDTO> rentalOrders = new ArrayList();

    public RentalsDTO(List<RentalOrder> rentals) {
        for (RentalOrder r : rentals) {
            this.rentalOrders.add(new RentalDTO(r));
        }
    }
    
    
    
}

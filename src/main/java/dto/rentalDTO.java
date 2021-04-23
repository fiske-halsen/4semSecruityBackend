package dto;

import entities.RentalOrder;
import entities.Car;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class rentalDTO {

    public String userName;
    public List<carDTO> products = new ArrayList();
    public int rentalDays;
    public Date rentalDate;

    public rentalDTO(RentalOrder order) {
        this.userName = order.getUser().getUserName();
        this.rentalDate = order.getRentalDate();
        this.rentalDays = rentalDays;

        for (Car car : order.getCars()) {
            this.products.add(new carDTO(car));
        }

    }
}


package dto;

import entities.Car;
import java.util.Objects;

public class CarDTO {
    
    public String brand;
    public String model;
    public int year;
    public double pricePerDay;
    

    public CarDTO(Car car) {
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.pricePerDay = car.getPricePerDay();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarDTO other = (CarDTO) obj;
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        return true;
    }
    
    
    
}

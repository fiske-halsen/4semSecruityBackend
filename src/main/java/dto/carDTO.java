
package dto;

import entities.Car;

public class carDTO {
    
    public String brand;
    public String model;
    public int year;
    public double pricePerDay;
    

    public carDTO(Car car) {
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.year = car.getYear();
        this.pricePerDay = car.getPricePerDay();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}


package dto;

import entities.Car;
import java.util.List;


public class carsDTO {
    
    
    public List<carDTO> products;
    
    public carsDTO(List<Car> products){
        for (Car product : products) {
            this.products.add(new carDTO(product));
        }
        
    }
    
}

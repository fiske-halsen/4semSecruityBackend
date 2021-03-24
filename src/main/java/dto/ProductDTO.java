
package dto;

import entities.Product;

public class ProductDTO {
    
    public String brand;
    public String model;
    public int year;
    public double price;

    public ProductDTO(Product product) {
        this.brand = product.getBrand();
        this.model = product.getModel();
        this.year = product.getYear();
        this.price = product.getPrice();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}

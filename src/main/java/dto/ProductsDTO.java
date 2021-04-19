
package dto;

import entities.Product;
import java.util.List;


public class ProductsDTO {
    
    
    public List<ProductDTO> products;
    
    public ProductsDTO(List<Product> products){
        for (Product product : products) {
            this.products.add(new ProductDTO(product));
        }
        
    }
    
}

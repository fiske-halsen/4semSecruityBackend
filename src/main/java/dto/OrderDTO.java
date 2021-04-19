package dto;

import entities.FullOrder;
import entities.Product;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    public String userName;
    public List<ProductDTO> products = new ArrayList();

    public OrderDTO(FullOrder order) {
        this.userName = order.getUser().getUserName();

        for (Product product : order.getProducts()) {
            this.products.add(new ProductDTO(product));
        }

    }
}

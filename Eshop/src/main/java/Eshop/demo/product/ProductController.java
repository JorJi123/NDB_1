package Eshop.demo.product;

import Eshop.demo.client.ClientService;
import Eshop.demo.order.ItemDTO;
import Eshop.demo.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;

    @PutMapping("/products")
    public ResponseEntity saveProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return new ResponseEntity<>("id: "+ product.getId(), HttpStatus.OK);
    }
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(@RequestParam(required = false, value = "category") String category){
        if(category != null) return productService.getByCategory(category);
        return productService.getProducts();
    }
    @GetMapping("/products/{id}")
    public Product getProductDetailsById(@PathVariable("id") String id){
        return productService.getProductDetailsById(id);
    }
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
    }

    @DeleteMapping("/cleanup")
    public void cleanup(){
        productService.cleanup();
        clientService.cleanup();
        orderService.cleanup();
    }


}

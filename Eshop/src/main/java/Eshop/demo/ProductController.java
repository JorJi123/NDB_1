package Eshop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PutMapping("/products")
    public ResponseEntity saveProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return new ResponseEntity<>("id: "+ product.getId(), HttpStatus.OK);
    }
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
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



}

package Eshop.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    @Autowired
    ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void saveProduct(Product product){
    if(product.getId() == null || product.getCategory() == null || product.getName() == null || product.getPrice() == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid arguments");
    productRepository.save(product);
    }

    public void cleanup(){
        productRepository.deleteAll();
    }
    public List<ProductDTO> getProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            productDTOList.add(new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory()));
        });
        return productDTOList;
    }
    public Product getProductDetailsById(String id){
        if(productRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        return productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(String id){
        if(productRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        productRepository.deleteById(id);
    }

    public List<ProductDTO> getByCategory(String category){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productRepository.findByCategory(category).forEach(product -> {
            productDTOList.add(new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory()));
        });
        return productDTOList;
    }

}

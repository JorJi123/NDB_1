package Eshop.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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
        return productRepository.findById(id).orElse(null);
    }
    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }


}

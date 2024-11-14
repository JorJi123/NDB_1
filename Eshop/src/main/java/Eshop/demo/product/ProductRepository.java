package Eshop.demo.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    ArrayList<Product> findByCategory(String category);
}

package Eshop.demo.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository

public interface OrderRepository extends MongoRepository<Order, String> {
    ArrayList<Order> findByClientId(String clientId);
    void deleteByClientId(String clientId);
}

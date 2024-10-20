package Eshop.demo.order;

import Eshop.demo.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public ArrayList<Order> getOrdersById (String clientId){
       return orderRepository.findByClientId(clientId);
    }

    public List<ItemDTO> getTopProducts(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("items"),
                Aggregation.group("items.productId").
                        sum("items.quantity").as("quantity"),
                Aggregation.project()
                        .and("_id").as("productId")
                        .and("quantity").as("quantity"),
                Aggregation.sort(Sort.by(Sort.Order.desc("quantity")))
        );
        AggregationResults<ItemDTO> results = mongoTemplate.aggregate(aggregation, "orders", ItemDTO.class);
        return results.getMappedResults().subList(0,10);
    }
}

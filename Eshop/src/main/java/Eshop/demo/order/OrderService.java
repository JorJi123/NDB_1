package Eshop.demo.order;

import Eshop.demo.client.Client;
import Eshop.demo.client.ClientDTO;
import Eshop.demo.client.ClientRepository;
import Eshop.demo.product.Product;
import Eshop.demo.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;

    public Order saveOrder(Order order){
        if (order.getClientId() == null || order.getItems() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid arguments");
        if (clientRepository.findById(order.getClientId()).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client ID is missing");
        orderRepository.save(order);
        return order;
    }

    public ArrayList<Order> getOrdersById (String clientId){
       return orderRepository.findByClientId(clientId);
    }

    public Long getTotalOrderCount(){
        return orderRepository.count();
    }
    public void cleanup(){
        orderRepository.deleteAll();
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
        if(results.getMappedResults().size() < 10) return results.getMappedResults();
        else return results.getMappedResults().subList(0, 10);
    }

    public List<ClientDTO> getTopClients(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("clientId")
                        .count().as("totalOrders"),
                Aggregation.project()
                        .and("_id").as("clientId")
                        .and("totalOrders").as("totalOrders"),
                Aggregation.sort(Sort.by(Sort.Order.desc("totalOrders")))
        );
        AggregationResults<ClientDTO> results = mongoTemplate.aggregate(aggregation, "orders", ClientDTO.class);
        if(results.getMappedResults().size() < 10) return results.getMappedResults();
        else return results.getMappedResults().subList(0, 10);
    }

    public Double getTotalOrderValue(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("items"),
                Aggregation.lookup("products", "items.productId", "_id", "productDetails"),
                Aggregation.unwind("productDetails"),
                Aggregation.project()
                        .andExpression("items.quantity * productDetails.price").as("itemTotalValue"),

                Aggregation.group().sum("itemTotalValue").as("totalValue")
        );

        AggregationResults<TotalValueResult> result = mongoTemplate.aggregate(aggregation, "orders", TotalValueResult.class);

        TotalValueResult totalValueResult = result.getUniqueMappedResult();

        return totalValueResult.getTotalValue();
    }
    public class TotalValueResult {
        private Double totalValue;

        public Double getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(Double totalValue) {
            this.totalValue = totalValue;
        }
    }

}


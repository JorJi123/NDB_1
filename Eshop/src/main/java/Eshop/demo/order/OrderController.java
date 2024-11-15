package Eshop.demo.order;

import Eshop.demo.client.Client;
import Eshop.demo.client.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PutMapping("/orders")
    public Order createOrder(@RequestBody Order order){
        return orderService.saveOrder(order);
    }
    @GetMapping("/clients/{clientId}/orders")
    public ArrayList<Order> getClientOrders(@PathVariable String clientId){
        return orderService.getOrdersById(clientId);
    }

    @GetMapping("/statistics/top/products")
    public List<ItemDTO> getProductsByQuantity(){
        return  orderService.getTopProducts();
    }

    @GetMapping("/statistics/orders/total")
    public ResponseEntity getTotalOrderCount(){
        return new ResponseEntity<>("TotalValue: " + orderService.getTotalOrderCount(), HttpStatus.OK);
    }

    @GetMapping("/statistics/top/clients")
    public List<ClientDTO> getClientByOrderAmount(){
        return orderService.getTopClients();
    }

    @GetMapping("/statistics/orders/totalValue")
    public Double getTotalValue(){
        return orderService.getTotalOrderValue();
    }
}

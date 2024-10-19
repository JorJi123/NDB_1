package Eshop.demo.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PutMapping("/orders")
    public void createOrder(@RequestBody Order order){
        orderService.saveOrder(order);
    }
    @GetMapping("/clients/{clientId}/orders")
    public ArrayList<Order> getClientOrders(@PathVariable String clientId){
        return orderService.getOrdersById(clientId);
    }

    @GetMapping("/statistics/top/products")
    public List<ItemDTO> getProductsByQuantity(){
        return  orderService.getTopProducts();
    }

}

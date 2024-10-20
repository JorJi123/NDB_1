package Eshop.demo.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Setter
@Getter
@Document(collection = "orders")
public class Order {
    private String clientId;

    private ArrayList<ItemDTO> items;
}

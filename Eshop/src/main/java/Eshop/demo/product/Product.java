package Eshop.demo.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String category;

    private String description;
    private Float price;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setPrice(Float price){
        this.price = price;
    }

    public String getDescription(){
        return this.description;
    }
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getCategory() {
        return this.category;
    }
    public Float getPrice() {
        return this.price;
    }
}

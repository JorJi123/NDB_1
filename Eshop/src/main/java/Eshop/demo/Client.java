package Eshop.demo;

import org.springframework.data.annotation.Id;

public class Client {
    @Id
    private String id;
    private String name;
    private String email;

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
}


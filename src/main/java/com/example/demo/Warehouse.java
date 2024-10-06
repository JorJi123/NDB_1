package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("warehouse")
public class Warehouse {
    @Id
    private String id;
    private String address;
    private String area;

   public String  getId(){
        return id;
    }
    public String  getAddress(){
        return address;
    }
    public String  getArea(){
        return area;
    }
//    public Warehouse(String id, String address, String area) {
//        this.id = "S" + id;
//        this.address = address;
//        this.area = area;
//    }
}

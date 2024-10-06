package com.example.demo;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("inventory")
public class Inventory {
    private String id;
    private int amount;

    public String getId(){
        return id;
    }
    public int getAmount(){
        return amount;
    }
}

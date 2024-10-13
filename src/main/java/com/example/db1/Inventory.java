package com.example.db1;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("inventory")
public class Inventory {
    @Id
    private String id;
    private int amount;

    public String getId(){
        return id;
    }
    public int getAmount(){
        return amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

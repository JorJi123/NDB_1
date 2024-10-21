package com.example.db1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final Jedis jedis;
    private Environment env;
    @Autowired
    InventoryService(InventoryRepository inventoryRepository, Environment env){
        this.env = env;
        this.inventoryRepository = inventoryRepository;
        this.jedis = new Jedis(this.env.getProperty("spring.redis.host"), 6379);
    }

    public Set<String> getInventories(String warehouseId){
        if(!jedis.sismember("warehouse", warehouseId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys nurodytu ID nerastas");
        String warehouseInventorySetKey = "warehouse:" + warehouseId;
        return jedis.smembers(warehouseId);
    }

    public long addInventoryToWarehouse(String warehouseId, Inventory inventory){
        if(!jedis.sismember("warehouse", warehouseId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys nurodytu ID nerastas");
        inventoryRepository.save(inventory);
        return jedis.sadd(warehouseId, inventory.getId());
    }


    public String getInventoryAmount(String whId, String inId){
        if(!jedis.sismember("warehouse", whId) || !jedis.sismember("inventory", inId) || !jedis.sismember(whId, inId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys nurodytu ID nerastas");
        return jedis.hget("inventory:" + inId, "amount");
    }

    public long setInventoryAmount(String whId, String inId, int newAmount){
        if(!jedis.sismember("warehouse", whId) || !jedis.sismember("inventory", inId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys nurodytu ID nerastas");
        return jedis.hset("inventory:" + inId, "amount", Integer.toString(newAmount));
    }

    public long deleteInventory(String whId, String inId){
        if(!jedis.sismember("warehouse", whId) || !jedis.sismember("inventory", inId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys nurodytu ID nerastas");
        inventoryRepository.delete(inventoryRepository.findById(inId).get());
        return jedis.srem(whId, inId);
    }

    public long addOrLessenInventoryAmount(String whId, String inId, int value){
        if(!jedis.sismember("warehouse", whId) || !jedis.sismember("inventory", inId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys nurodytu ID nerastas");
        return jedis.hincrBy("inventory:" + inId, "amount", value);
    }

}
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
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
        this.jedis = new Jedis(this.env.getProperty("spring.redis.host"), Integer.parseInt(this.env.getProperty("spring.redis.port")));
    }

    public List<Inventory> getInventories(String warehouseId){
        String warehouseInventorySetKey = "warehouse:" + warehouseId + ":inventory";
        Set<String> inventoriesId = jedis.smembers(warehouseInventorySetKey);
        return inventoriesId.stream().map(inventoryRepository::findById)
                .filter(Optional::isPresent)         // Filter out absent inventories
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Inventory addInventoryToWarehouse(String warehouseId,Inventory inventory){
        Inventory saveInventory = inventoryRepository.save(inventory);
        String warehouseInventorySetKey = "warehouse:" + warehouseId + ":inventory";
        jedis.sadd(warehouseInventorySetKey, saveInventory.getId());
        return saveInventory;
    }
}

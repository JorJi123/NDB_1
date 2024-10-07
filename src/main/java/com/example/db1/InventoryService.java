package com.example.db1;

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
        this.jedis = new Jedis(this.env.getProperty("spring.redis.host"), 6379);
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


    public int getInventoryAmount(String whId, String inId){
        Inventory inventory = inventoryRepository.findById(inId).orElse(null);
        return inventory.getAmount();
    }

    public Inventory setInventoryAmount(String whId, String inId, int newAmount){
        Inventory inventory = inventoryRepository.findById(inId).orElse(null);
        inventory.setAmount(newAmount);
        return inventoryRepository.save(inventory);
    }

    public long deleteInventory(String whId, String inId){
            String warehouseInventorySetKey = "warehouse:" + whId + ":inventory";
            System.out.println(warehouseInventorySetKey);
            long smt = jedis.srem(warehouseInventorySetKey, inId);
            return smt;
    }

    public Inventory addOrLessenInventoryAmount(String inId, int value){
        Inventory inventory = inventoryRepository.findById(inId).orElse(null);
        inventory.setAmount((inventory.getAmount() + value));
        return inventoryRepository.save(inventory);
    }

}
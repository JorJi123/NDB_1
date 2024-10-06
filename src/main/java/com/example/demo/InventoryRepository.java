package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import redis.clients.jedis.Jedis;

public interface InventoryRepository extends CrudRepository<Inventory, String> {

}

package com.example.db1;

import org.springframework.data.repository.CrudRepository;
import redis.clients.jedis.Jedis;

public interface InventoryRepository extends CrudRepository<Inventory, String> {

}

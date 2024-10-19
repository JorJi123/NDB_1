package com.example.db1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import redis.clients.jedis.Jedis;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final Jedis jedis;
    private Environment env;
    @Autowired
    WarehouseService(WarehouseRepository warehouseRepository, Environment env){
        this.env = env;
        this.warehouseRepository = warehouseRepository;
        this.jedis = new Jedis(this.env.getProperty("spring.redis.host"), 6379);
    }

    public Warehouse getWarehouseById(String id) {
        if(warehouseRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys tokiu ID jau užregistruotas");
        return warehouseRepository.findById(id).orElse(null);
    }

    public  Warehouse saveWarehouse(Warehouse warehouse){
        if(warehouseRepository.findById(warehouse.getId()).isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sandėlys tokiu ID jau užregistruotas");
        return  warehouseRepository.save(warehouse);
    }
    void deleteWarehouseById(String id){
        if(warehouseRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sandėlys tokiu ID jau užregistruotas");
        warehouseRepository.deleteById(id);
    }

}

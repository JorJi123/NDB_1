package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private Environment env;
    @GetMapping("/warehouse/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Warehouse getTest(@PathVariable("id") String id){
        System.out.println(env.getProperty("spring.redis.port"));
        return warehouseService.getWarehouseById(id);
    }

    @PostMapping("/warehouse")
    @CrossOrigin(origins = "http://localhost:4200")
    public Warehouse saveWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.saveWarehouse(warehouse);
    }
}

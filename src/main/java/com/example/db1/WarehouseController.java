package com.example.db1;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/warehouse/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Warehouse getTest(@PathVariable("id") String id){
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/warehouse")
    @CrossOrigin(origins = "http://localhost:4200")
    public Warehouse saveWarehouse(@RequestBody Warehouse warehouse) throws JsonProcessingException {
        return warehouseService.saveWarehouse(warehouse);
    }
    @DeleteMapping("/warehouse/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteWarehouse(@PathVariable("id") String id){
        warehouseService.deleteWarehouseById(id);
    }

}






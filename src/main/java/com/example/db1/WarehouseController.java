package com.example.db1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/warehouse/{id}")
    public Warehouse getTest(@PathVariable("id") String id){
        return warehouseService.getWarehouseById(id);
    }

    @PutMapping("/warehouse")
    public Warehouse saveWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.saveWarehouse(warehouse);
    }
    @DeleteMapping("/warehouse/{id}")
    public void deleteWarehouse(@PathVariable("id") String id){
        warehouseService.deleteWarehouseById(id);
    }

}






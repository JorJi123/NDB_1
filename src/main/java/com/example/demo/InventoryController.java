package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/warehouse/{id}/inventory")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Inventory> getInventories(@PathVariable("id") String id){
        return inventoryService.getInventories(id);
    }
    @PostMapping("/warehouse/{id}/inventory")
    @CrossOrigin(origins = "http://localhost:4200")
    public Inventory saveInventory(@PathVariable("id") String id, @RequestBody Inventory inventory){
        System.out.println(id);
        return inventoryService.addInventoryToWarehouse(id, inventory);
    }

    @DeleteMapping("/warehouse/{wId}/inventory/{iId}")
    public long deleteInventory(@PathVariable("wId") String warehouseId, @PathVariable("iId") String inventoryId){
       return inventoryService.deleteInventory(warehouseId, inventoryId);
    }
}

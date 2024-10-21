package com.example.db1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class InventoryController {


    private InventoryService inventoryService;
    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/warehouse/{id}/inventory")
    @CrossOrigin(origins = "http://localhost:4200")
    public Set<String> getInventories(@PathVariable("id") String id) {
        return inventoryService.getInventories(id);
    }

    @PutMapping("/warehouse/{id}/inventory")
    @CrossOrigin(origins = "http://localhost:4200")
    public long saveInventory(@PathVariable("id") String id, @RequestBody Inventory inventory){
        System.out.println(id);
        return inventoryService.addInventoryToWarehouse(id, inventory);
    }
    @GetMapping("/warehouse/{id}/inventory/{inventoryId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public String getInventoryAmount(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId) {
       return inventoryService.getInventoryAmount(whId, inId);
    }

    @PostMapping("/warehouse/{id}/inventory/{inventoryId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public long SetInventoryAmount(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId, @RequestBody int newAmount) {
        return inventoryService.setInventoryAmount(whId, inId, newAmount);
    }
    @DeleteMapping("/warehouse/{id}/inventory/{inventoryId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public long deleteInventory(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId) {
        return inventoryService.deleteInventory(whId, inId);
    }

    @PostMapping("/warehouse/{id}/inventory/{inventoryId}/change")
    @CrossOrigin(origins = "http://localhost:4200")
    public long changeInventoryAmount(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId, @RequestBody int value) {
        return inventoryService.addOrLessenInventoryAmount(whId ,inId, value);
    }

}

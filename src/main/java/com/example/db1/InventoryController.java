package com.example.db1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class InventoryController {


    private InventoryService inventoryService;
    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/warehouse/{id}/inventory")
    public List<Inventory> getInventories(@PathVariable("id") String id) {
        return inventoryService.getInventories(id);
    }

    @PutMapping("/warehouse/{id}/inventory")
    public Inventory saveInventory(@PathVariable("id") String id, @RequestBody Inventory inventory){
        System.out.println(id);
        return inventoryService.addInventoryToWarehouse(id, inventory);
    }
    @GetMapping("/warehouse/{id}/inventory/{inventoryId}")
    public int getInventoryAmount(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId) {
       return inventoryService.getInventoryAmount(whId, inId);
       //return inventoryService.getInventories(id);
    }

    @PostMapping("/warehouse/{id}/inventory/{inventoryId}")
    public Inventory SetInventoryAmount(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId, @RequestBody int newAmount) {
        return inventoryService.setInventoryAmount(whId, inId, newAmount);
    }
    @DeleteMapping("/warehouse/{id}/inventory/{inventoryId}")
    public long SetInventoryAmount(@PathVariable("id") String whId, @PathVariable("inventoryId") String inId) {
        return inventoryService.deleteInventory(whId, inId);
    }

    @PostMapping("/warehouse/{id}/inventory/{inventoryId}/change")
    public Inventory SetInventoryAmount(@PathVariable("inventoryId") String inId, @RequestBody int value) {
        return inventoryService.addOrLessenInventoryAmount(inId, value);
    }

}

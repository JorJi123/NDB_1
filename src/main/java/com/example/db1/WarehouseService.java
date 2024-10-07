package com.example.db1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Autowired
    WarehouseService(WarehouseRepository warehouseRepository){
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse getWarehouseById(String id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public  Warehouse saveWarehouse(Warehouse warehouse){
        return  warehouseRepository.save(warehouse);
    }
    void deleteWarehouseById(String id){
        warehouseRepository.deleteById(id);
    }

}

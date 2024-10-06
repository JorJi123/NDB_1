package com.example.demo;

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
        //Warehouse warehouse1 = new Warehouse(warehouse.getId(), warehouse.getAddress(), warehouse.getAddress());
        return  warehouseRepository.save(warehouse);
    }
}

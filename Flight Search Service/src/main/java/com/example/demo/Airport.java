package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Airport {
    @Id
    private String code;
    private String name;
    private Integer numberOfTerminals;
    private String address;

    Airport(AirportDTO airportDTO) {
        this.code = airportDTO.getCode();
        this.name = airportDTO.getName();
        this.numberOfTerminals = airportDTO.getNumberOfTerminals();
        this.address = airportDTO.getAddress();
    }
}

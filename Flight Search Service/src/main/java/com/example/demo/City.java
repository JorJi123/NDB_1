package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class City {
    @Id
    private String name;
    private String country;
    @Relationship(type = "AIRPORT")
    private List<Airport> airports = new ArrayList<>();
    public void addAirport(Airport airport){
        this.airports.add(airport);
    }
    City(City city){
        this.name = city.getName();
        this.country = city.getCountry();
    }
}

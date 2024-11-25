package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    @Id
    public String name;

    public String country;
    CityDTO(City city){
        this.name = city.getName();
        this.country = city.getCountry();
    }
}

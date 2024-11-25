package com.example.demo;

import jdk.jfr.FlightRecorder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class FlightDTO {
    @Id
    private String number;
    private String fromAirport;
    private String toAirport;
    private Double price;
    private Double flightTimeInMinutes;
    private String operator;
    private String fromCity;
    private String toCity;

}
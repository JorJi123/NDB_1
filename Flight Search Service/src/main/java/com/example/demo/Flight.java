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
public class Flight {
    @Id
    private String number;
    @Relationship(type = "DEPARTURE", direction = Relationship.Direction.OUTGOING)
    private Airport fromAirport;
    @Relationship(type = "ARRIVAL", direction = Relationship.Direction.OUTGOING)
    private Airport toAirport;
    private Double price;
    private Integer flightTimeInMinutes;
    private String operator;

}

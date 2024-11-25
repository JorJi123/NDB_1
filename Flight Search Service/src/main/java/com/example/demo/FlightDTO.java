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
    private Integer flightTimeInMinutes;
    private String operator;
    public FlightDTO(Flight flight, Airport fromAirport, Airport toAirport) {
        this.number = flight.getNumber();
        this.fromAirport = fromAirport != null ? fromAirport.getCode() : null;
        this.toAirport = toAirport != null ? toAirport.getCode() : null;
        this.price = flight.getPrice();
        this.flightTimeInMinutes = flight.getFlightTimeInMinutes();
        this.operator = flight.getOperator();
    }
}
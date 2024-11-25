package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FlightFromCityDTO {
    String fromAirport;
    String toAirport;
    List<String> flights;
    double price;
    double flightTimeInMinutes;
}

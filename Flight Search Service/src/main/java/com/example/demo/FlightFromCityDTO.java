package com.example.demo;

import lombok.Data;

import java.util.List;

@Data
public class FlightFromCityDTO {
    String fromAirport;
    String toAirport;
    List<String> flights;
    double price;
    double timeInMinutes;
}

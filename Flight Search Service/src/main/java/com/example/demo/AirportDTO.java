package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class AirportDTO {
    private String code;
    private String name;
    private Integer numberOfTerminals;
    private String address;
    private String city;


}

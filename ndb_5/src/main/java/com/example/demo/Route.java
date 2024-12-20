package com.example.demo;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    public String id;
    private int distance;
    public String fromStation;
    public String toStation;





}


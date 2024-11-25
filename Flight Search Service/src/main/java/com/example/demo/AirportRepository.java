package com.example.demo;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface AirportRepository extends Neo4jRepository<Airport, String> {

    // Custom query to find an airport by its primary key (code)

    @Query("MATCH (c:City)-[:AIRPORT]->(airport:Airport)\n" +
            "WHERE airport.code = $code\n" +
            "RETURN airport.code AS code, airport.name AS name, airport.numberOfTerminals AS numberOfTerminals, airport.address AS address, c.name AS city")
    AirportDTO findByCode(@Param("code") String code);

    @Query("MATCH (c:City)-[:AIRPORT]->(airport:Airport)\n" +
            "WHERE c.name = $city\n" +
            "RETURN airport.code AS code, airport.name AS name, airport.numberOfTerminals AS numberOfTerminals, airport.address AS address, c.name AS city")
    List<AirportDTO> findAllByCity(@Param("city") String city);
}


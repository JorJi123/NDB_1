package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends Neo4jRepository<City, String> {
    @Query("MATCH (city:City) WHERE city.country = $country RETURN city")
    List<City> findAllByCountry(String country);
    @Query("MATCH (city:City) WHERE city.name = $name AND city.country = $country RETURN DISTINCT city")
    City findByNameAndCountry(String name, String country);

    @Query("MATCH (city:City) RETURN city")
    List<City> findAll();

    @Query("MATCH (city:City {name: $cityName}) " +
            "MERGE (airport:Airport {code: $airportCode}) " +
            "SET airport.name = $airportName, " +
            "    airport.numberOfTerminals = $numberOfTerminals, " +
            "    airport.address = $address " +
            "MERGE (city)-[:AIRPORT]->(airport)")
    void registerAirport(@Param("cityName") String cityName,
                          @Param("airportCode") String airportCode,
                          @Param("airportName") String airportName,
                          @Param("numberOfTerminals") Integer numberOfTerminals,
                          @Param("address") String address);



}

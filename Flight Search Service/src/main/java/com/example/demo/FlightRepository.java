package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends Neo4jRepository<Flight, String> {

 @Query("""
 MATCH (flight:Flight {number: $number})
 OPTIONAL MATCH (flight)-[dep:DEPARTURE]->(fromAirport:Airport)
 OPTIONAL MATCH (flight)-[arr:ARRIVAL]->(toAirport:Airport)
 RETURN flight, collect(dep), collect(arr), fromAirport, toAirport
 """)
 Flight findByNumber(@Param("number") String number);

}

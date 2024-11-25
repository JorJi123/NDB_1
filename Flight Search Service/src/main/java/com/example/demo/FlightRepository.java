package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FlightRepository extends Neo4jRepository<Flight, String> {

 @Query("""
        MATCH (flight:Flight {number: $number})
        OPTIONAL MATCH (flight)-[dep:DEPARTURE]->(from:Airport)
        OPTIONAL MATCH (flight)-[arr:ARRIVAL]->(to:Airport)
        OPTIONAL MATCH (c:City)-[air:AIRPORT]->(from)
        RETURN flight.number AS number, from.name AS fromAirport, to.code AS toAirport, flight.price AS price, flight.flightTimeInMinutes AS flightTimeInMinutes, flight.operator AS operator, c.name AS fromCity;
 """)
 FlightDTO findByNumber(@Param("number") String number);

 @Query("  MATCH (c:City)-[:AIRPORT]->(a:Airport)<-[:DEPARTURE]-(f:Flight),\n" +
         "  (arrivalCity:City)-[:AIRPORT]->(a_1:Airport)<-[:ARRIVAL]-(f)\n  " +
         "  WHERE c.name = $fromCity AND arrivalCity.name = $toCity\n" +
         "  RETURN a.code AS fromAirport, a_1.code AS toAirport, COLLECT(f.number) AS flights, SUM(f.price) AS price, SUM(f.flightTimeInMinutes) AS timeInMinutes")
 List<Map<String, Object>> getByCities(@Param("fromCity") String fromCity, @Param("toCity") String toCity);

}

package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RouteRepository extends Neo4jRepository<Route, String> {

    @Query("MATCH (r:Route)-[:TRAVEL]->(s1:Station), (r)-[:TRAVEL]->(s2:Station) " +
            "WHERE s1.code = $fromStationId AND s2.code = $toStationId " +
            "RETURN r LIMIT 1")
    Route findDirectRoute(String fromStationId, String toStationId);

    @Query("MATCH (r1:Route)-[:TRAVEL]->(s1:Station), (r2:Route)-[:TRAVEL]->(s2:Station) " +
            "WHERE s1.code = $fromStationId AND s2.code = $toStationId " +
            "RETURN [r1, r2] LIMIT 1")
    ArrayList<Route> findConnectingRoutes(String fromStationId, String toStationId);

    @Query( "MATCH (from:Station {code: $fromStation}), (to:Station {code: $toStation}) " +
            "CREATE (route:Route {id: $id, distance: $distance, fromStation: $fromStation, toStation: $toStation}) " +
            "WITH route, from, to " +
            "MERGE (ticket_info:TicketInfo {fromStation: route.fromStation, toStation: route.toStation, price: 0.14 * route.distance + 0.3, routeId: route.id}) " +
            "CREATE (from)-[:TRAVEL]->(route)-[:TRAVEL]->(to)" +
            "CREATE (route)-[:TICKET]->(ticket_info)" +
            "RETURN route")
    Route createRoute(@Param("id") String id,
                      @Param("distance") int distance,
                      @Param("fromStation") String fromStation,
                      @Param("toStation") String toStation);

}


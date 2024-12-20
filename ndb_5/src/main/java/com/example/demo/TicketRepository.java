package com.example.demo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends Neo4jRepository<Ticket, String> {

    @Query("MATCH (route:Route {fromStation:$fromStation, toStation: $toStation})-[:TICKET]->(ticket_info:TicketInfo)" +
            "Create (ticket:Ticket {id: $id, fromStation: ticket_info.fromStation, toStation: ticket_info.toStation, route: ticket_info.routeId, fullName: $fullName, price: ticket_info.price})" +
            "CREATE (ticket_info)-[:REGISTERED]->(ticket)" +
            "RETURN ticket")
    Ticket saveTicket(@Param("id") String id,
                      @Param("fullName") String fullName,
                      @Param("fromStation") String fromStation,
                      @Param("toStation") String toStation);


    @Query("MATCH p = shortestPath((from:Station {code: $fromStation})-[:TRAVEL*]->(to:Station {code: $toStation})) " +
            "UNWIND nodes(p) AS n " +
            "WITH n " +
            "MATCH (n:Route)-[:TICKET]->(ticket:TicketInfo) " +
            "RETURN ticket.routeId AS routeId, ticket.fromStation AS fromStation, ticket.toStation AS toStation, ticket.price AS price ")
    List<TicketInfo> getTickets(@Param("fromStation") String fromStation,
                                @Param("toStation") String toStation);
}

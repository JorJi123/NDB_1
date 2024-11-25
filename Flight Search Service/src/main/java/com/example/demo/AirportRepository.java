package com.example.demo;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
@Repository
public interface AirportRepository extends Neo4jRepository<Airport, String> {

    // Custom query to find an airport by its primary key (code)
    @Query("MATCH (airport:Airport {code: $code}) RETURN airport")
    Airport findByCode(@Param("code") String code);
}


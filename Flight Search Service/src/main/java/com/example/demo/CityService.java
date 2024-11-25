package com.example.demo;

import org.neo4j.driver.Value;
import org.neo4j.driver.summary.ResultSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    public CityService(CityRepository cityRepository, AirportRepository airportRepository, FlightRepository flightRepository) {
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }
    public ResponseEntity<String> saveCity(City city){

            cityRepository.save(city);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\n" + "name :"+ city.getName() + "\ncountry : " + city.getCountry() + "\n}");
    }
    public City getCityByName(String name){
        return cityRepository.findById(name).orElse(null);
    }
    public List<CityDTO> getAllCities(String country){
        if(country == null){
            return cityRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        else

            return cityRepository.findAllByCountry(country).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

    }

    public ResponseEntity<Airport> registerAirport(String cityName, Airport airport){
        cityRepository.registerAirport(cityName, airport.getCode(), airport.getName(), airport.getNumberOfTerminals(), airport.getAddress());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(airport);
    }
    public CityDTO convertToDTO(City city) {
        return new CityDTO(city.getName(), city.getCountry());
    }

    public List<AirportDTO> getAllAirportsInCity(String cityName){
        return airportRepository.findAllByCity(cityName);
    }

    public AirportDTO getAirportByCode(String code){
        return airportRepository.findByCode(code);
    }

    public ResponseEntity<FlightDTO> registerFlight(FlightDTO flightDto){
     Airport toAirport = new Airport(airportRepository.findByCode(flightDto.getToAirport())) ;
     Airport fromAirport = new Airport(airportRepository.findByCode(flightDto.getFromAirport()));

     Flight flight = new Flight(
             flightDto.getNumber(),
             fromAirport,
             toAirport,
             flightDto.getPrice(),
             flightDto.getFlightTimeInMinutes(),
             flightDto.getOperator()
     );
        flightRepository.save(flight);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(flightDto);

    }
        @Transactional(readOnly = true)
        public ResponseEntity<FlightDTO> getFlightByCode(String flightCode) {
            String query = """
            MATCH (flight:Flight {number: $number})
            OPTIONAL MATCH (flight)-[dep:DEPARTURE]->(from:Airport)
            OPTIONAL MATCH (flight)-[arr:ARRIVAL]->(to:Airport)
            OPTIONAL MATCH (c:City)-[air:AIRPORT]->(from)
            OPTIONAL MATCH (c_1:City)-[a:AIRPORT]->(to)
            RETURN flight.number AS number, from.code AS fromAirport, to.code AS toAirport, flight.price AS price, flight.flightTimeInMinutes AS flightTimeInMinutes, flight.operator AS operator, c.name AS fromCity, c_1.name AS toCity;
            """;

            return neo4jClient.query(query)
                    .bind(flightCode).to("number") // Bind flightCode to the query parameter
                    .fetchAs(FlightDTO.class)
                    .mappedBy((typeSystem, record) -> new FlightDTO(
                            record.get("number").asString(),
                            record.get("fromAirport").asString(),
                            record.get("toAirport").asString(),
                            record.get("price").asDouble(),
                            record.get("flightTimeInMinutes").asDouble(),
                            record.get("operator").asString(),
                            record.get("fromCity").asString(),
                            record.get("toCity").asString()
                    ))
                    .one() // Fetch a single result
                    .map(flight -> ResponseEntity.status(HttpStatus.OK).body(flight))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        @Transactional
        public List<FlightFromCityDTO> getByCities(String fromCity, String toCity){
            String query = """
            MATCH (c:City)-[:AIRPORT]->(a:Airport)<-[:DEPARTURE]-(f:Flight),
                  (arrivalCity:City)-[:AIRPORT]->(a_1:Airport)<-[:ARRIVAL]-(f)
            WHERE c.name = $fromCity AND arrivalCity.name = $toCity
            RETURN a.code AS fromAirport, 
                   a_1.code AS toAirport, 
                   COLLECT(f.number) AS flights, 
                   SUM(f.price) AS price, 
                   SUM(f.flightTimeInMinutes) AS timeInMinutes
            """;

            return neo4jClient.query(query)
                    .bind(fromCity).to("fromCity")
                    .bind(toCity).to("toCity")
                    .fetchAs(FlightFromCityDTO.class)
                    .mappedBy((typeSystem, record) -> new FlightFromCityDTO(
                            record.get("fromAirport").asString(),
                            record.get("toAirport").asString(),
                            record.get("flights").asList(Value::asString),
                            record.get("price").asDouble(),
                            record.get("timeInMinutes").asDouble()
                    ))
                    .all().stream().toList();
        }


        @Transactional
        public void delete() {
            String query = "MATCH (n) DETACH DELETE n";
            neo4jClient.query(query).run();
        }



}

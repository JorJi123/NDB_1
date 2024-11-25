package com.example.demo;

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
            return ResponseEntity.status(HttpStatus.CREATED).body("{\n" + "name :"+ city.getName() + "\ncountry : " + city.getCountry() + "\n}");
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
        return ResponseEntity.status(HttpStatus.CREATED).body(airport);
    }
    public CityDTO convertToDTO(City city) {
        return new CityDTO(city.getName(), city.getCountry());
    }

    public List<Airport> getAllAirportsInCity(String cityName){

        return cityRepository.findById(cityName).orElse(null).getAirports();
    }

    public Airport getAirportByCode(String code){
        return airportRepository.findByCode(code);
    }

    public ResponseEntity<FlightDTO> registerFlight(FlightDTO flightDto){
     Airport toAirport = airportRepository.findByCode(flightDto.getToAirport());
     Airport fromAirport = airportRepository.findByCode(flightDto.getFromAirport());

     Flight flight = new Flight(
             flightDto.getNumber(),
             fromAirport,
             toAirport,
             flightDto.getPrice(),
             flightDto.getFlightTimeInMinutes(),
             flightDto.getOperator()
     );
        flightRepository.save(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(flightDto);

    }
        @Transactional(readOnly = true)
        public ResponseEntity<FlightDTO> getFlightByCode(String flightCode) {
            Flight flight = flightRepository.findByNumber(flightCode);

            if (flight == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            FlightDTO flightDTO = new FlightDTO(
                    flight.getNumber(),
                    flight.getFromAirport() != null ? flight.getFromAirport().getCode() : null,
                    flight.getToAirport() != null ? flight.getToAirport().getCode() : null,
                    flight.getPrice(),
                    flight.getFlightTimeInMinutes(),
                    flight.getOperator()
            );

            return ResponseEntity.status(HttpStatus.OK).body(flightDTO);
        }

        public ResultSummary getByCities(String fromCity, String toCity){
            String cypherQuery = """
                           MATCH (f:Flight)-[:DEPARTURE]->(from:Airport),
                           (f)-[:ARRIVAL]->(to:Airport)
                           WHERE from.code ="$fromCity" AND to.code = "$toCity"
                           RETURN f;
                    """;
            return neo4jClient.query(cypherQuery)
                    .bind(fromCity).to("fromCity")
                    .bind(toCity).to("toCity")
                    .run();
        }


        @Transactional
        public void delete() {
            String query = "MATCH (n) DETACH DELETE n";
            neo4jClient.query(query).run();
        }



}

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
            FlightDTO flight = flightRepository.findByNumber(flightCode);

            if (flight == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }


            return ResponseEntity.status(HttpStatus.OK).body(flight);
        }

        @Transactional
        public FlightFromCityDTO getByCities(String fromCity, String toCity){
            return flightRepository.getByCities(fromCity, toCity);
        }


        @Transactional
        public void delete() {
            String query = "MATCH (n) DETACH DELETE n";
            neo4jClient.query(query).run();
        }



}

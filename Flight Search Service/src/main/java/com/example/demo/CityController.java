package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class    CityController {
    @Autowired
    private CityService cityService;

    @PutMapping("/cities")
    public ResponseEntity<String> saveCity (@RequestBody City city){
        return cityService.saveCity(city);
    }
    @GetMapping("/cities/{name}")
    public ResponseEntity<City> getCityByName(@PathVariable("name") String name){
        City city = cityService.getCityByName(name);
        if(city == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.OK).body(new City(city));
    }
    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getAllCities(@RequestParam(required = false) String country){
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllCities(country));
    }

    @PutMapping("/cities/{name}/airports")
    public ResponseEntity<Airport> registerAirport(@RequestBody Airport airport, @PathVariable("name") String cityName){
        return cityService.registerAirport(cityName, airport);
    }

    @GetMapping("/cities/{name}/airports")
    public ResponseEntity<List<Airport>>  getAllAirportsInCity(@PathVariable("name") String cityName){
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllAirportsInCity(cityName));
    }

    @GetMapping("/airports/{code}")
    public Airport getAirportByCode(@PathVariable("code") String code){
        return cityService.getAirportByCode(code);
    }
    @PutMapping("/flights")
    public ResponseEntity<FlightDTO> registerFlight(@RequestBody FlightDTO flightDTO){
        return cityService.registerFlight(flightDTO);
    }
    @GetMapping("/flights/{flightNumber}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable String flightNumber){
        return cityService.getFlightByCode(flightNumber);
    }

    @GetMapping("/search/flights/{fromCity}/{toCity}")
    public ResponseEntity<?> findByCities(@PathVariable String fromCity, @PathVariable String toCity) {
        return ResponseEntity.ok(cityService.getByCities(fromCity, toCity));
    }

    @PostMapping("/cleanup")
    public ResponseEntity<?> deleteEverything(){
        cityService.delete();
        return  ResponseEntity.ok("");
    }
}

package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RouteController {
    @Autowired
    private RouteService routeService;

    @Autowired
    private ObjectMapper objectMapper;

    //station////////////////////////////////////////////////
    @PutMapping("/stations")
    public ResponseEntity<String> saveStation (@RequestBody Station station){
        routeService.saveStation(station);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\ncode: " + station.getCode()+ "\nname: "
                + station.getName() + "\ncurrentKilometer: "
                + "\ntime: " + station.getTime() + " \n}");
    }

    @GetMapping("/stations")
    public ResponseEntity<ArrayList<Station>> getAllStations(){
        return ResponseEntity.status(HttpStatus.OK).body(routeService.getAllStations());
    }
    ///////////////////////////////////////////////////////////

    //route////////////////////////////////////////////////////
    @PutMapping("/routes")
    ResponseEntity<String> saveRoute(@RequestBody Route route) throws JsonProcessingException {
        routeService.saveRoute(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString(route));
    }
    @GetMapping("/routes/{id}")
    ResponseEntity<Route> getRoute(@PathVariable("id") String id){
        routeService.getRoute(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.getRoute(id));
    }

    @GetMapping("/routes")
    ResponseEntity<ArrayList<Route>> getAllRoutes(){
        routeService.getAllRoutes();
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.getAllRoutes());
    }

    ///////////////////////////////////////////////////////////

    ///Ticket////////////////////////////////////////////////////////
    @PutMapping("/tickets")
    Ticket addTicket(@RequestBody TicketDTO ticketDTO){
        return routeService.saveTicket(ticketDTO);
    }

    @GetMapping("/tickets/{fromStation}/{toStation}")
    List<TicketInfo> getTickets(@PathVariable String fromStation, @PathVariable String toStation){
        return routeService.getTickets(fromStation, toStation);
    }


}

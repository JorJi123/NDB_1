package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;

    public RouteService(StationRepository stationRepository, RouteRepository routeRepository, TicketRepository ticketRepository) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.ticketRepository = ticketRepository;
    }


    //station////////////////////////////////////////////////
    public void saveStation(Station station){
        stationRepository.save(station);
    }
    public ArrayList<Station> getAllStations(){
        return (ArrayList<Station>) stationRepository.findAll();
    }
    ///////////////////////////////////////////////////////////

    //route////////////////////////////////////////////////////
    public void saveRoute(Route route){
        routeRepository.createRoute(route.getId(),
                                    route.getDistance(),
                                    route.getFromStation(),
                                    route.getToStation());
    }

    public Route getRoute(String routeId){
        return routeRepository.findById(routeId).orElse(null);
    }
    public ArrayList<Route> getAllRoutes(){
        return (ArrayList<Route>) routeRepository.findAll();
    }



    ////Ticket/////////////////////////////////////////////////
    public Ticket saveTicket(TicketDTO ticketDTO){
        return ticketRepository.saveTicket(ticketDTO.getId(), ticketDTO.getFullName(), ticketDTO.getFromStation(), ticketDTO.getToStation());
    }

    public List<TicketInfo> getTickets (String fromStation, String toStation) {
        return ticketRepository.getTickets(fromStation, toStation);
    }
}


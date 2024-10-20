package Eshop.demo.client;

import Eshop.demo.order.OrderRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client){
        if(client.getId() == null || client.getName() == null || client.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid arguments");
        clientRepository.save(client);
    }

    public Client getClientById(String id){
        if(clientRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteClientById(String id){
        if(clientRepository.findById(id).isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        orderRepository.deleteByClientId(id);
        clientRepository.deleteById(id);

    }
    public void cleanup(){
        clientRepository.deleteAll();
    }
}


package Eshop.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }

    public Client getClientById(String id){
        return clientRepository.findById(id).orElse(null);
    }

    public void deleteClientById(String id){
        clientRepository.deleteById(id);
    }

}


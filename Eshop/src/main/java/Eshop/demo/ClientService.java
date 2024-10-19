package Eshop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }


}


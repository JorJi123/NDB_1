package Eshop.demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PutMapping("/clients")
    public ResponseEntity saveClient(@RequestBody Client client){
        clientService.saveClient(client);
        return new ResponseEntity<>("id: "+ client.getId(), HttpStatus.OK);
    }
    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable("id") String id){
        return clientService.getClientById(id);
    }
    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable("id") String id){
         clientService.deleteClientById(id);
    }


}

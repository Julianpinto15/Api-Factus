package factusBackend.application.services;

import factusBackend.domain.model.Client;
import factusBackend.domain.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findClientByIdentificationNumber(String identificationNumber) {
        return clientRepository.findByIdentificationNumber(identificationNumber);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }
}
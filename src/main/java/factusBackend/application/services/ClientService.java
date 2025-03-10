package factusBackend.application.services;

import factusBackend.domain.model.Client;
import factusBackend.domain.repositories.ClientRepository;
import factusBackend.infrastructure.repositories.JpaClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final JpaClientRepository clientRepository;

    @Autowired
    public ClientService(JpaClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client saveClient(Client client) {
        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            logger.error("Error al guardar el cliente", e);
            throw new RuntimeException("Error al guardar el cliente", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Client> findClientByIdentification(String identification) {
        try {
            return clientRepository.findByIdentification(identification);
        } catch (Exception e) {
            logger.error("Error al buscar el cliente por identificación", e);
            throw new RuntimeException("Error al buscar el cliente por identificación", e);
        }
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }


    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }
}
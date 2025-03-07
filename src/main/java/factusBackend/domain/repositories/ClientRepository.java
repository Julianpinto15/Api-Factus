package factusBackend.domain.repositories;

import factusBackend.domain.model.Client;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Long id);
    Optional<Client> findByIdentification(String identification); // Cambiar aquí
    void deleteById(Long id);
}

package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Client;
import factusBackend.domain.repositories.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, Long>, ClientRepository {
    @Override
    Optional<Client> findByIdentificationNumber(String identificationNumber);
}
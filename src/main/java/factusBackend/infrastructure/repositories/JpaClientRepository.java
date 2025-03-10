package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, Long> {
}
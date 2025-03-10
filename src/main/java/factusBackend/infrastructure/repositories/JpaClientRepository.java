package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Client;
import factusBackend.domain.repositories.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.identification = :identification")
    Optional<Client> findByIdentification(@Param("identification") String identification);
}


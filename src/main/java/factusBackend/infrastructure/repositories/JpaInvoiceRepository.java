
package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Invoice;
import factusBackend.domain.repositories.InvoiceRepository;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaInvoiceRepository extends JpaRepository<Invoice, Long>, InvoiceRepository {
    @Override
    @Query("SELECT i FROM Invoice i WHERE i.factusId = :factusId")
    Optional<Invoice> findByFactusId(@Param("factusId") String factusId);

    @Override
    @Query("SELECT i FROM Invoice i WHERE i.client.id = :clientId")
    List<Invoice> findByClient_Id(@Param("clientId") Long clientId);
}
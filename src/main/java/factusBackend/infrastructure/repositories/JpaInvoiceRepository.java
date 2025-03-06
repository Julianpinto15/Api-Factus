
package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Invoice;
import factusBackend.domain.repositories.InvoiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaInvoiceRepository extends JpaRepository<Invoice, Long>, InvoiceRepository {
    @Override
    Optional<Invoice> findByFactusId(String factusId);

    @Override
    List<Invoice> findByClientId(Long clientId);
}
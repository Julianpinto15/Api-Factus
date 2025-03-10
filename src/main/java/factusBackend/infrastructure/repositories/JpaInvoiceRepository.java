package factusBackend.infrastructure.repositories;

import factusBackend.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaInvoiceRepository extends JpaRepository<Invoice, Long> {
}
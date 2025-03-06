package factusBackend.domain.repositories;

import factusBackend.domain.model.Invoice;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long id);
    Optional<Invoice> findByFactusId(String factusId);
    List<Invoice> findByClientId(Long clientId);
    List<Invoice> findAll();
}
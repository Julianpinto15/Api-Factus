package factusBackend.application.usecases;

import factusBackend.domain.model.Invoice;
import factusBackend.domain.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;

    public ValidateInvoiceUseCase(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice execute(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }
}
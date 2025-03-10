package factusBackend.application.usecases;

import factusBackend.domain.model.Invoice;
import factusBackend.domain.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateInvoiceUseCase {

    private final InvoiceRepository invoiceRepository;

    public CreateInvoiceUseCase(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice execute(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
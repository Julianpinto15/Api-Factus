package factusBackend.application.usecases;

import factusBackend.application.services.InvoiceService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateInvoiceUseCase {

    private final InvoiceService invoiceService;

    public ValidateInvoiceUseCase(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public Map<String, Object> execute(String invoiceId) {
        return invoiceService.validateInvoice(invoiceId);
    }
}
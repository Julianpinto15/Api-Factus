
package factusBackend.application.usecases;

import factusBackend.application.services.InvoiceService;
import factusBackend.domain.model.Invoice;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateInvoiceUseCase {

    private final InvoiceService invoiceService;

    public CreateInvoiceUseCase(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public InvoiceResponseDTO execute(InvoiceRequestDTO requestDTO) {
        // Aquí puedes añadir validaciones adicionales si es necesario
        return invoiceService.createInvoice(requestDTO);
    }
}
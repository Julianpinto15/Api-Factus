package factusBackend.presentation.controllers;

import factusBackend.application.services.InvoiceService;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Mono<InvoiceResponseDTO> createInvoice(@RequestBody InvoiceRequestDTO invoiceRequest) {
        return invoiceService.createInvoice(invoiceRequest);
    }

    @GetMapping("/{invoiceNumber}")
    public Mono<InvoiceResponseDTO> getInvoice(@PathVariable String invoiceNumber) {
        return invoiceService.getInvoice(invoiceNumber);
    }
}
package factusBackend.application.services;

import factusBackend.application.usecases.CreateInvoiceUseCase;
import factusBackend.application.usecases.ValidateInvoiceUseCase;
import factusBackend.domain.model.Invoice;
import factusBackend.domain.model.NumberingRange;
import factusBackend.infrastructure.adapters.FactusApiAdapter;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class InvoiceService {

    private final FactusApiAdapter factusApiAdapter;
    private final AuthenticationService authenticationService;
    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final ValidateInvoiceUseCase validateInvoiceUseCase;
    private final NumberingRangeService numberingRangeService;
    private final WebClient webClient;

    public InvoiceService(FactusApiAdapter factusApiAdapter,
                          AuthenticationService authenticationService,
                          CreateInvoiceUseCase createInvoiceUseCase,
                          ValidateInvoiceUseCase validateInvoiceUseCase,
                          NumberingRangeService numberingRangeService,
                          WebClient webClient) {
        this.factusApiAdapter = factusApiAdapter;
        this.authenticationService = authenticationService;
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.validateInvoiceUseCase = validateInvoiceUseCase;
        this.numberingRangeService = numberingRangeService;
        this.webClient = webClient;
    }

    public Mono<InvoiceResponseDTO> createAndValidateInvoice(InvoiceRequestDTO requestDTO) {
        return numberingRangeService.getActiveNumberingRange()
                .flatMap(activeRange -> {
                    requestDTO.setNumberingRangeId(activeRange.getId());
                    return authenticationService.getAccessToken()
                            .flatMap(token -> webClient.post()
                                    .uri("/bills/validate")
                                    .header("Authorization", "Bearer " + token)
                                    .bodyValue(requestDTO)
                                    .retrieve()
                                    .bodyToMono(InvoiceResponseDTO.class)
                                    .onErrorResume(WebClientResponseException.class, e -> {
                                        if (e.getStatusCode().value() == 422) {
                                            String errorBody = e.getResponseBodyAsString();
                                            return Mono.error(new RuntimeException("Error de validación: " + errorBody));
                                        }
                                        return Mono.error(e);
                                    }));
                });
    }

    @Transactional
    public Mono<InvoiceResponseDTO> createInvoice(InvoiceRequestDTO invoiceRequest) {
        return authenticationService.getAccessToken()
                .flatMap(token -> factusApiAdapter.createInvoice(invoiceRequest, token))
                .map(response -> {
                    // Map to Invoice entity and save
                    Invoice invoice = mapToInvoice(invoiceRequest, response);
                    createLocalInvoice(invoice);
                    return response; // Return the API response
                });
    }

    public Mono<InvoiceResponseDTO> getInvoice(String invoiceNumber) {
        return authenticationService.getAccessToken()
                .flatMap(token -> factusApiAdapter.getInvoice(invoiceNumber, token));
    }

    public Invoice createLocalInvoice(Invoice invoice) {
        return createInvoiceUseCase.execute(invoice);
    }

    public Invoice validateLocalInvoice(Long invoiceId) {
        return validateInvoiceUseCase.execute(invoiceId);
    }

    private Invoice mapToInvoice(InvoiceRequestDTO request, InvoiceResponseDTO response) {
        Invoice invoice = new Invoice();
        // Number: Temporarily hardcoded since getNumber() doesn't exist in InvoiceResponseDTO
        invoice.setNumber("INV-TEMP-001"); // Replace with actual field once InvoiceResponseDTO is provided

        // ReferenceCode: Available in request
        invoice.setReferenceCode(request.getReferenceCode() != null ? request.getReferenceCode() : "N/A");

        // Status: Temporarily hardcoded
        invoice.setStatus("PENDING"); // Replace with actual field once InvoiceResponseDTO is provided

        // Total: Calculate from items if available, or set a default
        String total = "0.0";
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            total = String.valueOf(request.getItems().stream()
                    .mapToDouble(item -> {
                        // Use getPrice() instead of getUnitPrice()
                        double price = item.getPrice() != null ? item.getPrice() : 0.0;
                        int quantity = item.getQuantity() != null ? item.getQuantity() : 0;
                        // Apply discount if available
                        double discountRate = item.getDiscountRate() != null ? item.getDiscountRate() : 0.0;
                        double discountedPrice = price * (1 - discountRate / 100);
                        return discountedPrice * quantity;
                    })
                    .sum());
        }
        invoice.setTotal(total);

        return invoice;
    }
}
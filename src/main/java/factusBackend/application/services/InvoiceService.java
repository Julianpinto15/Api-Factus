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
import reactor.core.scheduler.Schedulers;

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

    public Mono<InvoiceResponseDTO> createInvoice(InvoiceRequestDTO invoiceRequest) {
        return authenticationService.getAccessToken()
                .flatMap(token -> webClient.post()
                        .uri("/api/invoices")
                        .header("Authorization", "Bearer " + token)
                        .bodyValue(invoiceRequest)
                        .retrieve()
                        .bodyToMono(InvoiceResponseDTO.class));
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

        // Obtener valores reales de la respuesta de la API
        if (response.getData() != null) {
            // Intentar obtener el número de factura de la respuesta
            String documentNumber = "INV-TEMP-001"; // valor por defecto

            // Extraer código de referencia
            invoice.setReferenceCode(request.getReferenceCode() != null ?
                    request.getReferenceCode() : "N/A");

            // Establecer estado basado en la respuesta
            invoice.setStatus(response.getStatus() != null ?
                    response.getStatus() : "PENDING");

            // Calcular total desde los items
            String total = calculateTotal(request);
            invoice.setTotal(total);
        } else {
            // Configuración por defecto si no hay datos en la respuesta
            invoice.setNumber("INV-ERROR");
            invoice.setReferenceCode(request.getReferenceCode() != null ?
                    request.getReferenceCode() : "N/A");
            invoice.setStatus("ERROR");
            invoice.setTotal("0.0");
        }

        return invoice;
    }

    private String calculateTotal(InvoiceRequestDTO request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return "0.0";
        }

        double total = request.getItems().stream()
                .mapToDouble(item -> {
                    double price = item.getPrice() != null ? item.getPrice() : 0.0;
                    int quantity = item.getQuantity() != null ? item.getQuantity() : 0;
                    double discountRate = item.getDiscountRate() != null ? item.getDiscountRate() : 0.0;
                    double discountedPrice = price * (1 - discountRate / 100);
                    return discountedPrice * quantity;
                })
                .sum();

        return String.valueOf(total);
    }
}
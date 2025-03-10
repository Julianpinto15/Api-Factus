package factusBackend.application.services;

import factusBackend.application.usecases.CreateInvoiceUseCase;
import factusBackend.application.usecases.ValidateInvoiceUseCase;
import factusBackend.domain.model.Invoice;
import factusBackend.infrastructure.adapters.FactusApiAdapter;
import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class InvoiceService {

    private final FactusApiAdapter factusApiAdapter;
    private final AuthenticationService authenticationService;
    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final ValidateInvoiceUseCase validateInvoiceUseCase;
    private final NumberingRangeService numberingRangeService;

    public InvoiceService(FactusApiAdapter factusApiAdapter, AuthenticationService authenticationService,
                          CreateInvoiceUseCase createInvoiceUseCase, ValidateInvoiceUseCase validateInvoiceUseCase) {
        this.factusApiAdapter = factusApiAdapter;
        this.authenticationService = authenticationService;
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.validateInvoiceUseCase = validateInvoiceUseCase;
    }

    public Mono<InvoiceResponseDTO> createAndValidateInvoice(InvoiceRequestDTO requestDTO) {
        // Primero obtenemos un rango de numeración activo
        return numberingRangeService.getActiveNumberingRange()
                .flatMap(activeRange -> {
                    // Asignar el ID del rango de numeración a la solicitud
                    requestDTO.setNumberingRangeId(activeRange.getId());

                    // Continuar con la creación y validación de la factura
                    return authenticationService.getAccessToken()
                            .flatMap(token -> webClient.post()
                                    .uri("/bills/validate")
                                    .header("Authorization", "Bearer " + token)
                                    .bodyValue(requestDTO)
                                    .retrieve()
                                    .bodyToMono(InvoiceResponseDTO.class)
                                    .onErrorResume(WebClientResponseException.class, e -> {
                                        // Manejar el error 422 u otros errores
                                        if (e.getStatusCode().value() == 422) {
                                            // Intentar extraer los detalles del error
                                            String errorBody = e.getResponseBodyAsString();
                                            return Mono.error(new RuntimeException("Error de validación: " + errorBody));
                                        }
                                        return Mono.error(e);
                                    }));
                });
    }

    public Mono<InvoiceResponseDTO> createInvoice(InvoiceRequestDTO invoiceRequest) {
        return authenticationService.getAccessToken()
                .flatMap(token -> factusApiAdapter.createInvoice(invoiceRequest, token));
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
}
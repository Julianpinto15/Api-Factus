package factusBackend.application.services;

import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InvoiceService {

    private final WebClient webClient;
    private final AuthenticationService authenticationService;

    public InvoiceService(WebClient webClient, AuthenticationService authenticationService) {
        this.webClient = webClient;
        this.authenticationService = authenticationService;
    }

    public Mono<InvoiceResponseDTO> createInvoice(InvoiceRequestDTO invoiceRequest) {
        return authenticationService.getAccessToken()
                .flatMap(token -> webClient.post()
                        .uri("/v1/bills/validate")
                        .header("Authorization", "Bearer " + token)
                        .bodyValue(invoiceRequest)
                        .retrieve()
                        .bodyToMono(InvoiceResponseDTO.class));
    }

    public Mono<InvoiceResponseDTO> getInvoice(String invoiceNumber) {
        return authenticationService.getAccessToken()
                .flatMap(token -> webClient.get()
                        .uri("/v1/bills/show/{number}", invoiceNumber)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(InvoiceResponseDTO.class));
    }
}
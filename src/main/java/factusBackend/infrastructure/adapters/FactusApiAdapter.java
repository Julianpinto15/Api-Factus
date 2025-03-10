package factusBackend.infrastructure.adapters;

import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FactusApiAdapter {

    private final WebClient webClient;

    public FactusApiAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<InvoiceResponseDTO> createInvoice(InvoiceRequestDTO invoiceRequest, String token) {
        return webClient.post()
                .uri("/v1/bills/validate")
                .header("Authorization", "Bearer " + token)
                .bodyValue(invoiceRequest)
                .retrieve()
                .bodyToMono(InvoiceResponseDTO.class);
    }

    public Mono<InvoiceResponseDTO> getInvoice(String invoiceNumber, String token) {
        return webClient.get()
                .uri("/v1/bills/show/{number}", invoiceNumber)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(InvoiceResponseDTO.class);
    }
}
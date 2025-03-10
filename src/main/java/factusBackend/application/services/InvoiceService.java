package factusBackend.application.services;

import factusBackend.presentation.dtos.InvoiceRequestDTO;
import factusBackend.presentation.dtos.InvoiceResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InvoiceService {

    private final WebClient webClient;

    public InvoiceService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<InvoiceResponseDTO> createInvoice(InvoiceRequestDTO invoiceRequest) {
        return webClient.post()
                .uri("/v1/bills/validate")
                .bodyValue(invoiceRequest)
                .retrieve()
                .bodyToMono(InvoiceResponseDTO.class);
    }

    public Mono<InvoiceResponseDTO> getInvoice(String invoiceNumber) {
        return webClient.get()
                .uri("/v1/bills/show/{number}", invoiceNumber)
                .retrieve()
                .bodyToMono(InvoiceResponseDTO.class);
    }
}
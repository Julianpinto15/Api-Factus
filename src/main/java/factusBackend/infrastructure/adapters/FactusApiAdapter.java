package factusBackend.infrastructure.adapters;

import factusBackend.common.constans.ApiConstants;
import factusBackend.common.exceptions.ResourceNotFoundException;
import factusBackend.presentation.controllers.InvoiceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class FactusApiAdapter {

    private final WebClient webClient;
    private final AuthenticationAdapter authAdapter;
    private static final Logger logger = LoggerFactory.getLogger(FactusApiAdapter.class);

    public FactusApiAdapter(WebClient webClient, AuthenticationAdapter authAdapter) {
        this.webClient = webClient;
        this.authAdapter = authAdapter;
    }

    public Map<String, Object> createInvoice(Map<String, Object> invoiceData) {
        try {
            // Log para depuración
            logger.debug("Enviando solicitud a Factus API: {}", invoiceData);

            return webClient.post()
                    .uri(ApiConstants.FACTUS_CREATE_INVOICE_ENDPOINT)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(invoiceData)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), response -> {
                        // Log para depuración
                        logger.error("Error cliente al crear factura: {}", response.statusCode());
                        return response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new RuntimeException("Error cliente: " + error)));
                    })
                    .onStatus(status -> status.is5xxServerError(), response -> {
                        // Log para depuración
                        logger.error("Error servidor al crear factura: {}", response.statusCode());
                        return response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new RuntimeException("Error servidor: " + error)));
                    })
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            // Log para depuración
            logger.error("Error al crear factura: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Error al crear factura: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> validateInvoice(String invoiceId) {
        try {
            return webClient.post()
                    .uri(ApiConstants.FACTUS_VALIDATE_INVOICE_ENDPOINT, invoiceId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), response -> {
                        if (response.statusCode() == HttpStatus.UNAUTHORIZED) {
                            authAdapter.refreshAccessToken();
                            return Mono.error(new RuntimeException("Token expirado, por favor intente nuevamente"));
                        } else if (response.statusCode() == HttpStatus.NOT_FOUND) {
                            return Mono.error(new ResourceNotFoundException("Factura no encontrada con ID: " + invoiceId));
                        }
                        return response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new RuntimeException("Error cliente: " + error)));
                    })
                    .onStatus(status -> status.is5xxServerError(), response ->
                            response.bodyToMono(String.class)
                                    .flatMap(error -> Mono.error(new RuntimeException("Error servidor: " + error)))
                    )
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al validar factura: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> getCountries() {
        try {
            return webClient.get()
                    .uri(ApiConstants.FACTUS_COUNTRIES_ENDPOINT)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al obtener países: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> getMunicipalities() {
        try {
            return webClient.get()
                    .uri(ApiConstants.FACTUS_MUNICIPALITIES_ENDPOINT)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al obtener municipios: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> getTaxation() {
        try {
            return webClient.get()
                    .uri(ApiConstants.FACTUS_TAXATION_ENDPOINT)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al obtener tributos: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> getUnits() {
        try {
            return webClient.get()
                    .uri(ApiConstants.FACTUS_UNITS_ENDPOINT)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al obtener unidades de medida: " + e.getResponseBodyAsString(), e);
        }
    }

    public Map<String, Object> getNumberRanges() {
        try {
            return webClient.get()
                    .uri(ApiConstants.FACTUS_NUMBER_RANGES_ENDPOINT)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authAdapter.getAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al obtener rangos de numeración: " + e.getResponseBodyAsString(), e);
        }
    }
}
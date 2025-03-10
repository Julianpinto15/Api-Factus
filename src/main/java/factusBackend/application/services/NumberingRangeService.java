package factusBackend.application.services;

import factusBackend.domain.model.NumberingRange;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.common.exceptions.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class NumberingRangeService {

    private final WebClient webClient;
    private final AuthenticationService authService;

    public NumberingRangeService(WebClient webClient, AuthenticationService authService) {
        this.webClient = webClient;
        this.authService = authService;
    }

    public Flux<NumberingRange> getAllNumberingRanges() {
        return authService.getAccessToken()
                .flatMapMany(token -> webClient.get()
                        .uri("/numbering-ranges")
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(ResponseWrapper.class)
                        .flatMapMany(response -> {
                            if (response.getData() instanceof List) {
                                @SuppressWarnings("unchecked")
                                List<Object> data = (List<Object>) response.getData();
                                return Flux.fromIterable(data)
                                        .map(item -> convertToNumberingRange(item));
                            } else {
                                return Flux.empty();
                            }
                        }));
    }

    public Mono<NumberingRange> getNumberingRangeById(String id) {
        return authService.getAccessToken()
                .flatMap(token -> webClient.get()
                        .uri("/numbering-ranges/" + id)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(ResponseWrapper.class)
                        .flatMap(response -> {
                            if (response.getData() != null) {
                                return Mono.just(convertToNumberingRange(response.getData()));
                            } else {
                                return Mono.error(new ResourceNotFoundException("Rango de numeración no encontrado con ID: " + id));
                            }
                        }));
    }

    public Mono<NumberingRange> getActiveNumberingRange() {
        return getAllNumberingRanges()
                .filter(NumberingRange::getActive)
                .next()
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró un rango de numeración activo")));
    }

    public Mono<NumberingRange> createNumberingRange(NumberingRange range) {
        return authService.getAccessToken()
                .flatMap(token -> {
                    // Create request body according to the Postman example
                    Map<String, Object> requestBody = new LinkedHashMap<>();
                    requestBody.put("document", 21); // Document type ID
                    requestBody.put("prefix", range.getPrefix());
                    requestBody.put("from", range.getFromNumber());
                    requestBody.put("to", range.getToNumber());
                    requestBody.put("current", range.getCurrentNumber());
                    requestBody.put("resolution_number", range.getResolutionNumber());
                    requestBody.put("start_date", range.getStartDate());
                    requestBody.put("end_date", range.getEndDate());
                    requestBody.put("technical_key", range.getTechnicalKey());

                    return webClient.post()
                            .uri("/numbering-ranges")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(requestBody)
                            .retrieve()
                            .bodyToMono(ResponseWrapper.class)
                            .map(response -> {
                                if (response.getData() != null) {
                                    return convertToNumberingRange(response.getData());
                                } else {
                                    // If there's no data in the response, return the original range
                                    // This might need adjustment based on your API's behavior
                                    return range;
                                }
                            });
                });
    }

    public Mono<NumberingRange> updateNumberingRange(String id, NumberingRange range) {
        return authService.getAccessToken()
                .flatMap(token -> webClient.put()
                        .uri("/numbering-ranges/" + id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(convertToRequestMap(range))
                        .retrieve()
                        .bodyToMono(ResponseWrapper.class)
                        .map(response -> convertToNumberingRange(response.getData())));
    }

    public Mono<Void> deleteNumberingRange(String id) {
        return authService.getAccessToken()
                .flatMap(token -> webClient.delete()
                        .uri("/numbering-ranges/" + id)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .bodyToMono(Void.class));
    }

    private Map<String, Object> convertToRequestMap(NumberingRange range) {
        // Convert the NumberingRange object to a Map that matches the expected request format
        // Based on the Postman example
        return Map.of(
                "prefix", range.getPrefix(),
                "from", range.getFromNumber(),
                "to", range.getToNumber(),
                "current", range.getCurrentNumber(),
                "active", range.getActive()
                // Add other fields like resolution_number, start_date, etc. if needed
        );
    }

    private NumberingRange convertToNumberingRange(Object data) {
        // Implementar la conversión del objeto data al modelo NumberingRange
        // Esto dependerá de la estructura exacta que devuelve la API
        // Este es un ejemplo simplificado

        if (data instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) data;

            NumberingRange range = new NumberingRange();
            range.setId(map.get("id") != null ? map.get("id").toString() : null);
            range.setPrefix(map.getOrDefault("prefix", "").toString());
            range.setFromNumber(Integer.parseInt(map.getOrDefault("from", "0").toString()));
            range.setToNumber(Integer.parseInt(map.getOrDefault("to", "0").toString()));
            range.setCurrentNumber(Integer.parseInt(map.getOrDefault("current", "0").toString()));
            range.setActive(Boolean.parseBoolean(map.getOrDefault("active", "false").toString()));

            // Add additional fields if present in the response
            if (map.containsKey("resolution_number")) {
                range.setResolutionNumber(map.get("resolution_number").toString());
            }
            if (map.containsKey("start_date")) {
                range.setStartDate(map.get("start_date").toString());
            }
            if (map.containsKey("end_date")) {
                range.setEndDate(map.get("end_date").toString());
            }
            if (map.containsKey("technical_key")) {
                range.setTechnicalKey(map.get("technical_key").toString());
            }

            return range;
        }

        return new NumberingRange(); // Devolver un objeto vacío o manejar mejor este caso
    }
}
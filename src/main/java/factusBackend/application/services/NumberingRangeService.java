package factusBackend.application.services;

import factusBackend.domain.model.NumberingRange;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
                .filter(NumberingRange::isActive)
                .next()
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró un rango de numeración activo")));
    }

    private NumberingRange convertToNumberingRange(Object data) {
        // Implementar la conversión del objeto data al modelo NumberingRange
        // Esto dependerá de la estructura exacta que devuelve la API
        // Este es un ejemplo simplificado

        if (data instanceof java.util.Map) {
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> map = (java.util.Map<String, Object>) data;

            NumberingRange range = new NumberingRange();
            range.setId(map.get("id").toString());
            range.setPrefix(map.getOrDefault("prefix", "").toString());
            range.setFromNumber(Integer.parseInt(map.getOrDefault("from", "0").toString()));
            range.setToNumber(Integer.parseInt(map.getOrDefault("to", "0").toString()));
            range.setCurrentNumber(Integer.parseInt(map.getOrDefault("current", "0").toString()));
            range.setActive(Boolean.parseBoolean(map.getOrDefault("active", "false").toString()));

            return range;
        }

        return new NumberingRange(); // Devolver un objeto vacío o manejar mejor este caso
    }
}
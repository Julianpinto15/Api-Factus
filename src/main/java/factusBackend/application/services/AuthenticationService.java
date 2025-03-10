package factusBackend.application.services;

import factusBackend.domain.model.AuthRequest;
import factusBackend.infrastructure.adapters.AuthenticationAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {

    private final WebClient webClient;
    private final AuthenticationAdapter authAdapter;

    @Value("${factus.api.client-id}")
    private String clientId;

    @Value("${factus.api.client-secret}")
    private String clientSecret;

    @Value("${factus.api.email}")
    private String email;

    @Value("${factus.api.password}")
    private String password;

    public AuthenticationService(WebClient webClient, AuthenticationAdapter authAdapter) {
        this.webClient = webClient;
        this.authAdapter = authAdapter;
    }

    public Mono<String> getAccessToken() {
        AuthRequest authRequest = new AuthRequest(clientId, clientSecret, email, password);
        return authAdapter.authenticate(authRequest)
                .map(AuthenticationAdapter.AuthResponse::getAccessToken);
    }

    // Alternative implementation using WebClient directly if needed
    public Mono<String> getAccessTokenDirect() {
        return webClient.post()
                .uri("/oauth/token")
                .bodyValue(new AuthRequest(clientId, clientSecret, email, password))
                .retrieve()
                .bodyToMono(AuthenticationAdapter.AuthResponse.class)
                .map(AuthenticationAdapter.AuthResponse::getAccessToken);
    }
}
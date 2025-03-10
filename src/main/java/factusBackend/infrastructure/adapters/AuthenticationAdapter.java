package factusBackend.infrastructure.adapters;

import factusBackend.domain.model.AuthRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class AuthenticationAdapter {

    private final WebClient webClient;

    public AuthenticationAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AuthResponse> authenticate(AuthRequest authRequest) {
        return webClient.post()
                .uri("/oauth/token")
                .bodyValue(authRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    // Clase interna para representar la respuesta de autenticación
    public static class AuthResponse {
        private String accessToken;
        private String tokenType;
        private long expiresIn;
        private String refreshToken;

        // Getters y setters
        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public long getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
package factusBackend.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {

    private final WebClient webClient;

    @Value("${factus.api.client-id}")
    private String clientId;

    @Value("${factus.api.client-secret}")
    private String clientSecret;

    @Value("${factus.api.email}")
    private String email;

    @Value("${factus.api.password}")
    private String password;

    public AuthenticationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getAccessToken() {
        return webClient.post()
                .uri("/oauth/token")
                .bodyValue(new AuthRequest(clientId, clientSecret, email, password))
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(AuthResponse::getAccessToken);
    }

    private static class AuthRequest {
        private final String grantType = "password";
        private final String clientId;
        private final String clientSecret;
        private final String username;
        private final String password;

        public AuthRequest(String clientId, String clientSecret, String username, String password) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.username = username;
            this.password = password;
        }

        public String getGrantType() {
            return grantType;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    private static class AuthResponse {
        private String accessToken;
        private String tokenType;
        private long expiresIn;
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public long getExpiresIn() {
            return expiresIn;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
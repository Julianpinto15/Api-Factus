package factusBackend.infrastructure.adapters;

import com.fasterxml.jackson.annotation.JsonProperty;
import factusBackend.domain.model.AuthRequest;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class AuthenticationAdapter {

    private final WebClient webClient;

    public AuthenticationAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AuthResponse> authenticate(AuthRequest authRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", authRequest.getGrantType());
        formData.add("client_id", authRequest.getClientId());
        formData.add("client_secret", authRequest.getClientSecret());
        formData.add("username", authRequest.getUsername());
        formData.add("password", authRequest.getPassword());

        return webClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    // Clase interna para representar la respuesta de autenticación
    public static class AuthResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("expires_in")
        private long expiresIn;

        @JsonProperty("refresh_token")
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
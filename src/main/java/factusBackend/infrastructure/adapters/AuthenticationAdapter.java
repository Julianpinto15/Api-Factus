package factusBackend.infrastructure.adapters;

import factusBackend.common.constans.ApiConstants;
import factusBackend.common.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class AuthenticationAdapter {

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String email;
    private final String password;

    private String accessToken;
    private String refreshToken;
    private long tokenExpiryTime;

    public AuthenticationAdapter(
            WebClient webClient,
            @Value("${factus.api.client-id}") String clientId,
            @Value("${factus.api.client-secret}") String clientSecret,
            @Value("${factus.api.email}") String email,
            @Value("${factus.api.password}") String password) {
        this.webClient = webClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.email = email;
        this.password = password;
    }

    public String getAccessToken() {
        if (accessToken == null || System.currentTimeMillis() >= tokenExpiryTime) {
            authenticate();
        }
        return accessToken;
    }

    public void authenticate() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", email);
        formData.add("password", password);

        try {
            Map<String, Object> response = webClient.post()
                    .uri(ApiConstants.FACTUS_AUTH_ENDPOINT)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                accessToken = (String) response.get("access_token");
                refreshToken = (String) response.get("refresh_token");
                int expiresIn = (Integer) response.get("expires_in");
                tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
            } else {
                throw new AuthenticationException("No se pudo autenticar con Factus API");
            }
        } catch (Exception e) {
            throw new AuthenticationException("Error al autenticar: " + e.getMessage());
        }
    }

    public void refreshAccessToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        try {
            Map<String, Object> response = webClient.post()
                    .uri(ApiConstants.FACTUS_AUTH_ENDPOINT)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                accessToken = (String) response.get("access_token");
                refreshToken = (String) response.get("refresh_token");
                int expiresIn = (Integer) response.get("expires_in");
                tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
            } else {
                throw new AuthenticationException("No se pudo refrescar el token");
            }
        } catch (Exception e) {
            throw new AuthenticationException("Error al refrescar token: " + e.getMessage());
        }
    }
}
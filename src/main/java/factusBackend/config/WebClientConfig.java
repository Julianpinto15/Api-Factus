package factusBackend.config;

import factusBackend.infrastructure.adapters.AuthenticationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api-sandbox.factus.com.co")
                .build();
    }

    @Bean
    public AuthenticationAdapter authenticationAdapter(WebClient webClient) {
        return new AuthenticationAdapter(webClient);
    }

}
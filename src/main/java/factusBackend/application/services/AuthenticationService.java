package factusBackend.application.services;

import factusBackend.infrastructure.adapters.AuthenticationAdapter;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationAdapter authAdapter;

    public AuthenticationService(AuthenticationAdapter authAdapter) {
        this.authAdapter = authAdapter;
    }

    public String authenticate() {
        authAdapter.authenticate();
        return authAdapter.getAccessToken();
    }

    public String getAccessToken() {
        return authAdapter.getAccessToken();
    }

    public void refreshToken() {
        authAdapter.refreshAccessToken();
    }
}
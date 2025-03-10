package factusBackend.domain.model;

public class AuthRequest {
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
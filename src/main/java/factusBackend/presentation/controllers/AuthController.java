package factusBackend.presentation.controllers;

import factusBackend.application.services.AuthenticationService;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.domain.model.AuthRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/token")
    public Mono<String> getToken() {
        return authenticationService.getAccessToken();
    }

    @PostMapping
    public Mono<ResponseEntity<ResponseWrapper<String>>> authenticate(@RequestBody(required = false) AuthRequest authRequest) {
        if (authRequest == null) {
            // Use default credentials but wrap the result correctly
            return authenticationService.getAccessToken()
                    .map(token -> ResponseEntity.ok(new ResponseWrapper<>("success", "Authentication successful", token)));
        }

        // Use custom credentials
        return authenticationService.authenticate(authRequest)
                .map(token -> ResponseEntity.ok(new ResponseWrapper<>("success", "Authentication successful", token)));
    }

}
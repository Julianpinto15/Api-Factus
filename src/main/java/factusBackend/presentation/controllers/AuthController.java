package factusBackend.presentation.controllers;

import factusBackend.application.services.AuthenticationService;
import factusBackend.application.services.UserService;
import factusBackend.common.constans.ApiConstants;
import factusBackend.common.dto.ResponseWrapper;
import factusBackend.presentation.dtos.AuthRequestDTO;
import factusBackend.presentation.dtos.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiConstants.API_BASE_PATH + ApiConstants.AUTH_PATH)
public class AuthController {

    private final AuthenticationService factusAuthService;
    private final UserService userService;

    public AuthController(AuthenticationService factusAuthService, UserService userService) {
        this.factusAuthService = factusAuthService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<AuthResponseDTO>> login(@RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO response = userService.authenticate(authRequest);
        return ResponseEntity.ok(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, response));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<String>> register(@RequestBody AuthRequestDTO authRequest) {
        userService.registerUser(authRequest.getUsername(), authRequest.getPassword(), "USER");
        return ResponseEntity.ok(new ResponseWrapper<String>(true, "Usuario registrado exitosamente", null));
    }

    @PostMapping("/factus/token")
    public ResponseEntity<ResponseWrapper<Map<String, String>>> getFactusToken() {
        String token = factusAuthService.authenticate();
        Map<String, String> tokenData = Map.of("access_token", token);
        return ResponseEntity.ok(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, tokenData));
    }

    @PostMapping("/factus/refresh")
    public ResponseEntity<ResponseWrapper<Map<String, String>>> refreshFactusToken() {
        factusAuthService.refreshToken();
        String token = factusAuthService.getAccessToken();
        Map<String, String> tokenData = Map.of("access_token", token);
        return ResponseEntity.ok(new ResponseWrapper<>(true, ApiConstants.SUCCESS_MESSAGE, tokenData));
    }
}
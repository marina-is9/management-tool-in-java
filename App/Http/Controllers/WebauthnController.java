import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/webauthn")
public class WebauthnController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private WebauthnService webauthnService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "Validation failed", result.getAllErrors());
        }

        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "The user email is already taken.");
        }

        try {
            Tenant tenant = new Tenant(UUID.randomUUID().toString(), registerRequest.getUsername(), registerRequest.getEmail());
            tenantRepository.save(tenant);

            User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), UUID.randomUUID().toString(), tenant.getId());
            userRepository.save(user);

            Object options = webauthnService.prepareAttestation(user);
            return ResponseHandler.success(ResponseCode.SUCCESS, options);

        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "An error occurred while processing the request.", e.getMessage());
        }
    }

    @PostMapping("/verify-register")
    public ResponseEntity<?> verifyRegister(@Validated @RequestBody VerifyRegisterRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "Validation failed", result.getAllErrors());
        }

        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (!userOpt.isPresent()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "The user with this email does not exist.");
        }

        User user = userOpt.get();
        try {
            webauthnService.validateAttestation(user, request);
            String token = generateToken(user);
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("token_type", "Bearer");
            tokenData.put("access_token", token);
            tokenData.put("expires_at", "some-date");

            return ResponseHandler.success(ResponseCode.CREATED, tokenData);

        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Verification failed.", e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "Validation failed", result.getAllErrors());
        }

        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (!userOpt.isPresent()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "The user email does not exist.");
        }

        User user = userOpt.get();
        try {
            Object options = webauthnService.prepareAssertion(user);
            return ResponseHandler.success(ResponseCode.SUCCESS, options);

        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Login preparation failed.", e.getMessage());
        }
    }

    @PostMapping("/verify-login")
    public ResponseEntity<?> verifyLogin(@Validated @RequestBody VerifyLoginRequest verifyLoginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "Validation failed", result.getAllErrors());
        }

        Optional<User> userOpt = userRepository.findByEmail(verifyLoginRequest.getEmail());
        if (!userOpt.isPresent()) {
            return ResponseHandler.error(ResponseCode.NOT_FOUND, "The user with this email does not exist.");
        }

        User user = userOpt.get();
        try {
            webauthnService.validateAssertion(user, verifyLoginRequest);
            String token = generateToken(user);

            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("token_type", "Bearer");
            tokenData.put("access_token", token);
            tokenData.put("expires_at", "some-date");

            return ResponseHandler.success(ResponseCode.SUCCESS, tokenData);

        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Verification failed.", e.getMessage());
        }
    }

    private String generateToken(User user) {
        // Implement token generation logic here
        return "generated-jwt-token";
    }
}

class RegisterRequest {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String username;
    // getters and setters
}

class VerifyRegisterRequest {
    @NotEmpty
    private String id;
    @NotEmpty
    private String type;
    @NotEmpty
    private String rawId;
    private String responseAttestationObject;
    private String responseClientDataJSON;
    @NotEmpty
    @Email
    private String email;
    // getters and setters
}

class LoginRequest {
    @NotEmpty
    @Email
    private String email;
    // getters and setters
}

class VerifyLoginRequest {
    @NotEmpty
    private String id;
    @NotEmpty
    private String type;
    @NotEmpty
    private String rawId;
    private String responseAuthenticatorData;
    private String responseClientDataJSON;
    private String responseSignature;
    @Email
    private String email;
    // getters and setters
}

class ResponseHandler {
    public static ResponseEntity<?> success(ResponseCode code, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code.getCode());
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.valueOf(code.getCode()));
    }

    public static ResponseEntity<?> error(ResponseCode code, String message) {
        return error(code, message, null);
    }

    public static ResponseEntity<?> error(ResponseCode code, String message, Object errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code.getCode());
        response.put("message", message);
        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.valueOf(code.getCode()));
    }
}

enum ResponseCode {
    SUCCESS(200),
    CREATED(201),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

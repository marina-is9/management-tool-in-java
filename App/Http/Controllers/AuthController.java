import com.example.api.ResponseCode;
import com.example.api.ResponseHandler;
import com.example.helpers.IDGenerator;
import com.example.models.*;
import com.example.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "User email is already taken", null);
        }

        Tenant tenant = new Tenant(IDGenerator.generate(), registerRequest.getUsername(), registerRequest.getEmail());
        tenantRepository.save(tenant);

        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), 
                             passwordEncoder.encode(registerRequest.getPassword()), tenant);
        userRepository.save(user);

        TokenData tokenData = tokenService.generateToken(user);

        return ResponseHandler.success(ResponseCode.CREATED, new RegisterResponse(user, tokenData));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseHandler.error(ResponseCode.UNAUTHORIZED, "Invalid credentials", null);
        }

        TokenData tokenData = tokenService.generateToken(user);
        return ResponseHandler.success(ResponseCode.SUCCESS, new LoginResponse(user, tokenData));
    }

    @PostMapping("/sendResetLinkEmail")
    public ResponseEntity<?> sendResetLinkEmail(@RequestBody @Valid EmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = IDGenerator.generate(); // generate token logic
        // Save the token in a reset-password database or cache

        // Send email logic

        return ResponseHandler.success(ResponseCode.SUCCESS, "Reset link sent successfully");
    }

    @PostMapping("/passwordReset")
    public ResponseEntity<?> passwordReset(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        // Retrieve token from request and validate it
        User user = userRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userRepository.save(user);

        // Invalidate the token in the reset-password database

        return ResponseHandler.success(ResponseCode.SUCCESS, "Password reset successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Implement logout functionality by revoking tokens

        return ResponseHandler.success(ResponseCode.SUCCESS, "Logout successful");
    }

    @PostMapping("/sendInvitationEmail")
    public ResponseEntity<?> sendInvitationEmail(@RequestBody @Valid EmailRequest emailRequest) {
        User user = userRepository.findByEmail(emailRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPassword() != null) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "User has already set a password", null);
        }

        String token = IDGenerator.generate();
        // Save the token to an invitations database

        // Send invitation email logic

        return ResponseHandler.success(ResponseCode.SUCCESS, "Invitation email sent successfully");
    }
}

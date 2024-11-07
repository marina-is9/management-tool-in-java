package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebAuthnConfig {

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Master Switch
    |--------------------------------------------------------------------------
    |
    | This option may be used to disable WebAuthn.
    |
    */
    private boolean enable = true;

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Guard (Authentication Method)
    |--------------------------------------------------------------------------
    |
    | Here you may specify which authentication method WebAuthn will use.
    |
    */
    private String guard = "api";

    /*
    |--------------------------------------------------------------------------
    | Username / Email
    |--------------------------------------------------------------------------
    |
    | This value defines which model attribute should be considered as the username field.
    | Typically, this might be the email address of the users.
    |
    */
    private String username = "email";

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Routes Prefix/Subdomain
    |--------------------------------------------------------------------------
    |
    | Here you may specify which prefix WebAuthn will assign to all the routes.
    |
    */
    private String prefix = "webauthn";
    private String domain = null;

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Routes Middleware
    |--------------------------------------------------------------------------
    |
    | Here you may specify which middleware WebAuthn will assign to the routes.
    |
    */
    private String[] middleware = {"api"};

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Key Model
    |--------------------------------------------------------------------------
    |
    | Here you may specify the model used to create WebAuthn keys.
    |
    */
    private String model = "com.example.models.WebauthnKey";

    /*
    |--------------------------------------------------------------------------
    | Rate Limiting
    |--------------------------------------------------------------------------
    |
    | By default, WebAuthn will throttle logins. You can specify a custom rate limiter.
    |
    */
    private String loginLimiter = null;

    /*
    |--------------------------------------------------------------------------
    | Redirect Routes
    |--------------------------------------------------------------------------
    |
    | Redirect URLs for successful login and WebAuthn key creation.
    |
    */
    private String loginRedirect = null;
    private String registerRedirect = null;

    /*
    |--------------------------------------------------------------------------
    | View to Load After Middleware Login Request
    |--------------------------------------------------------------------------
    |
    | The name of the view template to load after WebAuthn authentication.
    |
    */
    private String authenticateView = "webauthn::authenticate";
    private String registerView = "webauthn::register";

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Logging
    |--------------------------------------------------------------------------
    |
    | Specify the channel to log WebAuthn messages.
    |
    */
    private String log = null;

    /*
    |--------------------------------------------------------------------------
    | Session Name
    |--------------------------------------------------------------------------
    |
    | Name of the session parameter to store the successful login.
    |
    */
    private String sessionName = "webauthn_auth";

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Challenge Length
    |--------------------------------------------------------------------------
    |
    | Length of the random string used in the challenge request.
    |
    */
    private int challengeLength = 32;

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Timeout (Milliseconds)
    |--------------------------------------------------------------------------
    |
    | Time that the caller is willing to wait for the call to complete.
    |
    */
    private long timeout = 60000;

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Extension Client Input
    |--------------------------------------------------------------------------
    |
    | Optional authentication extension.
    |
    */
    private String[] extensions = {};

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Icon
    |--------------------------------------------------------------------------
    |
    | URL which resolves to an image associated with the entity.
    |
    */
    private String icon = System.getenv("WEBAUTHN_ICON");

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Attestation Conveyance
    |--------------------------------------------------------------------------
    |
    | Specify the preference for the attestation conveyance during credential generation.
    |
    */
    private String attestationConveyance = "none";

    /*
    |--------------------------------------------------------------------------
    | WebAuthn Public Key Credential Parameters
    |--------------------------------------------------------------------------
    |
    | List of allowed cryptographic algorithm identifiers.
    |
    */
    private String[] publicKeyCredentialParameters = {
        "COSE_ALGORITHM_ES256", 
        "COSE_ALGORITHM_ES512", 
        "COSE_ALGORITHM_RS256", 
        "COSE_ALGORITHM_EDDSA", 
        "COSE_ALGORITHM_ES384"
    };

    /*
    |--------------------------------------------------------------------------
    | Credentials Attachment
    |--------------------------------------------------------------------------
    |
    | Specifies whether authentication is tied to the current device or a cross-platform device.
    |
    */
    private String attachmentMode = null;

    /*
    |--------------------------------------------------------------------------
    | User Presence and Verification
    |--------------------------------------------------------------------------
    |
    | Specifies whether user verification is required, preferred, or discouraged.
    |
    */
    private String userVerification = "preferred";

    /*
    |--------------------------------------------------------------------------
    | Userless (One-Touch, Typeless) Login
    |--------------------------------------------------------------------------
    |
    | Specifies whether userless login is required, preferred, or discouraged.
    |
    */
    private String userless = null;

    // Getter and Setter Methods (if necessary)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    // Other getter/setter methods omitted for brevity

    @Bean
    public WebAuthnConfig webAuthnConfig() {
        return new WebAuthnConfig();
    }
}

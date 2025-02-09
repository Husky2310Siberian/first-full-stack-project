package gr.aueb.network_book.auth;

import gr.aueb.network_book.email.EmailService;
import gr.aueb.network_book.email.EmailTemplateName;
import gr.aueb.network_book.role.IRoleRepository;
import gr.aueb.network_book.security.JwtService;
import gr.aueb.network_book.user.ITokenRepository;
import gr.aueb.network_book.user.IUserRepository;
import gr.aueb.network_book.user.Token;
import gr.aueb.network_book.user.User;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${spring.application.security.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role User not initialized"));

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail() ,
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
                );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    /**
     * Algorithm that generates a code coontaining 6-digits
     */
    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length()); // 0 -> 9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    /**
     * Takes an AuthenticationRequest object as input, which contains the user's email and password.
     * @param request
     * @return AuthenticationResponse, which probably contains a JWT token.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String , Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName" , user.getFullName());
        var jwtToken = jwtService.generateToken(claims , user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Retrieves the token from database and validate a new account if token has been expired
     * @param token
     */
    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));

        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired , a new token has beeb sent");
        }
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatesAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}

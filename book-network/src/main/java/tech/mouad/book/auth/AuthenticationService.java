package tech.mouad.book.auth;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.mouad.book.email.EmailService;
import tech.mouad.book.email.EmailTemplateName;
import tech.mouad.book.role.RoleRepository;
import tech.mouad.book.security.JwtService;
import tech.mouad.book.user.Token;
import tech.mouad.book.user.TokenRepository;
import tech.mouad.book.user.User;
import tech.mouad.book.user.UserRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String urlConfirmation;

    public void register(RegisterRequestDto registerRequestDto) throws MessagingException {
        // get The roles
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        // create the user
        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        var user = User.builder().firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(user.getEmail(), user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT, urlConfirmation, "Activation account", newToken
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusSeconds(20))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String chars = "0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }


    public AuthenticateResponseDto authenticate(AuthenticateRequestDto requestDto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwt = jwtService.generateToken(claims, user);
        return AuthenticateResponseDto.builder().token(jwt).build();
    }
     // il ne faut pas qu il sois transaction
    public void activateAccount(String token) throws MessagingException {
        Token savedtoken = tokenRepository.findByToken(token)
                //todo better generation exception
                .orElseThrow(() -> new RuntimeException("The Token is not valid"));
        if (LocalDateTime.now().isAfter(savedtoken.getExpiredAt())) {
            sendValidationEmail(savedtoken.getUser());
            throw new RuntimeException("The token is Expired ,A new token has been sent");
        }
        var user = userRepository.findById(savedtoken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("The user doesn't exists"));
        user.setEnabled(true);
        userRepository.save(user);
        savedtoken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedtoken);
    }
}

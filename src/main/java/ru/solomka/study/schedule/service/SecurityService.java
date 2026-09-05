package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.api.model.security.UserRole;
import ru.solomka.study.schedule.configuration.TokenConfigurationProperties;
import ru.solomka.study.schedule.exception.AuthenticationException;
import ru.solomka.study.schedule.exception.CredentialValidationException;
import ru.solomka.study.schedule.exception.UserAlreadyExistsException;
import ru.solomka.study.schedule.security.AuthenticationType;
import ru.solomka.study.schedule.security.jwt.TokenFactory;
import ru.solomka.study.schedule.security.jwt.TokenPair;
import ru.solomka.study.schedule.security.jwt.TokenType;
import ru.solomka.study.schedule.utils.CredentialsValidator;

import java.time.Instant;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityService {

    TokenFactory tokenFactory;
    UserService userService;
    PasswordEncoder passwordEncoder;
    CredentialsValidator credentialsValidator;
    TokenConfigurationProperties tokenConfigurationProperties;

    public SecurityService(TokenFactory tokenFactory, UserService userService,
                           PasswordEncoder passwordEncoder, CredentialsValidator credentialsValidator,
                           TokenConfigurationProperties tokenConfigurationProperties) {
        this.tokenFactory = tokenFactory;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
        this.tokenConfigurationProperties = tokenConfigurationProperties;
    }

    public TokenPair login(String login, String password) {
        if (credentialsValidator.containsInUsernameForbiddenSymbols(login))
            throw new CredentialValidationException("Username contains forbidden symbols");

        User user = userService.getByUsername(login);

        if (!passwordEncoder.matches(password, user.passwordHash()))
            throw new AuthenticationException("Incorrect credentials");

        String accessToken = tokenFactory.generateToken(
                user,
                TokenType.ACCESS,
                tokenConfigurationProperties.getAccessTokenExpiration()
        );

        String refreshToken = tokenFactory.generateToken(
                user,
                TokenType.REFRESH,
                tokenConfigurationProperties.getRefreshTokenExpiration()
        );

        return new TokenPair(accessToken, refreshToken);
    }

    public User registration(String login, String password, AuthenticationType authenticationType) {
        credentialsValidator.validateCredentials(login, password, authenticationType == AuthenticationType.EMAIL);

        if (userService.existsByUsername(login))
            throw new UserAlreadyExistsException("User with username '%s' already exists".formatted(login));

        return userService.create(new User(
                login,
                passwordEncoder.encode(password),
                UserRole.GHOST,
                Instant.now()
        ));
    }
}

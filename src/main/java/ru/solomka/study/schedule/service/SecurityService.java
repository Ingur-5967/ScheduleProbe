package ru.solomka.study.schedule.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.solomka.study.schedule.api.model.security.RefreshToken;
import ru.solomka.study.schedule.api.model.user.User;
import ru.solomka.study.schedule.api.model.user.UserRole;
import ru.solomka.study.schedule.api.repository.UserRepository;
import ru.solomka.study.schedule.configuration.TokenConfigurationProperties;
import ru.solomka.study.schedule.exception.AuthenticationException;
import ru.solomka.study.schedule.exception.CredentialValidationException;
import ru.solomka.study.schedule.exception.UserAlreadyExistsException;
import ru.solomka.study.schedule.security.AuthenticationType;
import ru.solomka.study.schedule.security.jwt.TokenEntity;
import ru.solomka.study.schedule.security.jwt.TokenFactory;
import ru.solomka.study.schedule.security.jwt.TokenPair;
import ru.solomka.study.schedule.security.jwt.TokenType;
import ru.solomka.study.schedule.utils.CredentialsValidator;

import java.time.Instant;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityService {

    UserRepository userRepository;
    RefreshTokenService refreshTokenService;

    TokenFactory tokenFactory;
    PasswordEncoder passwordEncoder;
    CredentialsValidator credentialsValidator;
    TokenConfigurationProperties tokenConfigurationProperties;

    public SecurityService(TokenFactory tokenFactory, UserRepository userRepository, RefreshTokenService refreshTokenService,
                           PasswordEncoder passwordEncoder, CredentialsValidator credentialsValidator,
                           TokenConfigurationProperties tokenConfigurationProperties) {
        this.tokenFactory = tokenFactory;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.credentialsValidator = credentialsValidator;
        this.tokenConfigurationProperties = tokenConfigurationProperties;
    }

    public TokenPair login(String login, String password) {
        if (credentialsValidator.containsInUsernameForbiddenSymbols(login))
            throw new CredentialValidationException("Username contains forbidden symbols");

        User user = userRepository.getByUsername(login);

        if (!passwordEncoder.matches(password, user.passwordHash()))
            throw new AuthenticationException("Incorrect credentials");

        Pair<String, TokenEntity> accessToken = tokenFactory.generateToken(
                user,
                TokenType.ACCESS,
                tokenConfigurationProperties.getAccessTokenExpiration()
        );
        String accessTokenRepresentation = accessToken.getFirst();

        Pair<String, TokenEntity> refreshToken = tokenFactory.generateToken(
                user,
                TokenType.REFRESH,
                tokenConfigurationProperties.getRefreshTokenExpiration()
        );
        String refreshTokenRepresentation = refreshToken.getFirst();
        TokenEntity refreshTokenEntity = refreshToken.getSecond();

        refreshTokenService.create(new RefreshToken(
                refreshTokenEntity.id(),
                refreshTokenEntity.userId(),
                refreshTokenRepresentation,
                refreshTokenEntity.expiredAt()
        ));

        return new TokenPair(accessTokenRepresentation, refreshTokenRepresentation);
    }

    public User registration(String login, String password, AuthenticationType authenticationType) {
        credentialsValidator.validateCredentials(login, password, authenticationType == AuthenticationType.EMAIL);

        if (userRepository.existsByUsername(login))
            throw new UserAlreadyExistsException("User with username '%s' already exists".formatted(login));

        return userRepository.create(new User(
                login,
                passwordEncoder.encode(password),
                UserRole.GUEST,
                Instant.now()
        ));
    }
}

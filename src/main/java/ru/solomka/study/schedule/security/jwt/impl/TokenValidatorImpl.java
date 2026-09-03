package ru.solomka.study.schedule.security.jwt.impl;

import io.jsonwebtoken.JwtException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.exception.TokenPayloadExtractException;
import ru.solomka.study.schedule.security.jwt.TokenEntity;
import ru.solomka.study.schedule.security.jwt.TokenExtractor;
import ru.solomka.study.schedule.security.jwt.TokenValidator;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenValidatorImpl implements TokenValidator {

    TokenExtractor tokenExtractor;

    public TokenValidatorImpl(TokenExtractor tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public boolean validateToken(String token) {
        TokenEntity tokenEntity;
        try {
            tokenEntity = tokenExtractor.extract(token);
        } catch (JwtException e) {
            throw new TokenPayloadExtractException("Failed attempt extract token payload");
        }
        return this.isAccessTokenExpired(tokenEntity.expiredAt());
    }

    private boolean isAccessTokenExpired(Instant tokenExpiration) {
        return !tokenExpiration.isBefore(Instant.now());
    }
}

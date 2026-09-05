package ru.solomka.study.schedule.security.jwt.impl;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.UserRole;
import ru.solomka.study.schedule.exception.TokenPayloadExtractException;
import ru.solomka.study.schedule.security.jwt.TokenEntity;
import ru.solomka.study.schedule.security.jwt.TokenParser;
import ru.solomka.study.schedule.security.jwt.TokenType;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenParserImpl implements TokenParser {

    SecretKey secretKey;
    Logger log = LoggerFactory.getLogger(TokenParserImpl.class);

    public TokenParserImpl(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Optional<TokenEntity> parseAndValidateToken(String token) {
        try {
            TokenEntity tokenEntity = this.extract(token);

            if(tokenEntity.expiredAt().isBefore(Instant.now()))
                return Optional.empty();

            return Optional.of(tokenEntity);
        } catch (ExpiredJwtException e) {
            return Optional.empty();
        } catch (JwtException e) {
            throw new TokenPayloadExtractException("Failed attempt to extract token payload: " + e.getMessage());
        }
    }

    private TokenEntity extract(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new TokenEntity(
                    claims.get("id", Long.class),
                    claims.get("username", String.class),
                    UserRole.valueOf(claims.get("role", String.class)),
                    TokenType.valueOf(claims.get("type", String.class)),
                    Instant.ofEpochMilli(claims.get("expired_at", Long.class))
            );
        } catch (ExpiredJwtException e) {
            log.warn("Token expired for user: {}", e.getClaims().getSubject());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
            throw new JwtException("Unsupported JWT token", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new JwtException("Invalid JWT token", e);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature");
            throw new JwtException("Invalid JWT signature", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty");
            throw new JwtException("JWT claims string is empty", e);
        }
    }
}

package ru.solomka.study.schedule.security.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.UserRole;
import ru.solomka.study.schedule.security.jwt.TokenEntity;
import ru.solomka.study.schedule.security.jwt.TokenExtractor;
import ru.solomka.study.schedule.security.jwt.TokenType;

import javax.crypto.SecretKey;
import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenExtractorImpl implements TokenExtractor {

    SecretKey secretKey;

    public TokenExtractorImpl(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public TokenEntity extract(String token) {

        JwtParserBuilder jwtParserBuilder = Jwts.parser();
        jwtParserBuilder.verifyWith(secretKey);

        Claims claims = jwtParserBuilder.build()
                .parseSignedClaims(token)
                .getPayload();

        return new TokenEntity(
                claims.get("username", String.class),
                claims.get("role", UserRole.class),
                claims.get("type", TokenType.class),
                claims.get("expired_at", Instant.class)
        );
    }
}

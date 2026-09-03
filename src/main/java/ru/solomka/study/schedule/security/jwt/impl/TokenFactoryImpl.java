package ru.solomka.study.schedule.security.jwt.impl;

import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.security.User;
import ru.solomka.study.schedule.security.jwt.TokenFactory;
import ru.solomka.study.schedule.security.jwt.TokenType;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenFactoryImpl implements TokenFactory {

    SecretKey signKey;

    public TokenFactoryImpl(SecretKey signKey) {
        this.signKey = signKey;
    }

    @Override
    public String generateToken(User user, TokenType tokenType, Duration lifetime) {
        Instant now = Instant.now();
        Date expiredAt = Date.from(now.plus(lifetime));
        return Jwts.builder()
                .subject(user.username())
                .claims()
                .add("username", user.username())
                .add("role", user.role())
                .add("type", tokenType)
                .add("expired_at", expiredAt.toInstant().toEpochMilli())
                .and()
                .issuedAt(Date.from(now))
                .expiration(expiredAt)
                .signWith(signKey)
                .compact();
    }
}

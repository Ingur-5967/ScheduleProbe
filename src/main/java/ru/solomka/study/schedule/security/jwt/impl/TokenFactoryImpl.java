package ru.solomka.study.schedule.security.jwt.impl;

import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.api.model.user.User;
import ru.solomka.study.schedule.security.jwt.TokenEntity;
import ru.solomka.study.schedule.security.jwt.TokenFactory;
import ru.solomka.study.schedule.security.jwt.TokenType;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenFactoryImpl implements TokenFactory {

    SecretKey signKey;

    public TokenFactoryImpl(SecretKey signKey) {
        this.signKey = signKey;
    }

    @Override
    public Pair<String, TokenEntity> generateToken(User user, TokenType tokenType, Duration lifetime) {
        Instant now = Instant.now();
        Date expiredAt = Date.from(now.plus(lifetime));
        String token = Jwts.builder()
                .subject(user.username())
                .claims()
                .add("id", UUID.randomUUID())
                .add("user_id", user.id())
                .add("username", user.username())
                .add("role", user.role())
                .add("type", tokenType)
                .add("expired_at", expiredAt.toInstant().toEpochMilli())
                .and()
                .issuedAt(Date.from(now))
                .expiration(expiredAt)
                .signWith(signKey)
                .compact();

        return Pair.of(token, new TokenEntity(
                UUID.randomUUID(),
                user.id(),
                user.username(),
                user.role(),
                tokenType,
                expiredAt.toInstant()
        ));
    }
}

package ru.solomka.study.schedule.security.jwt;

import ru.solomka.study.schedule.api.model.security.User;

import java.time.Duration;

public interface TokenFactory {

    String generateToken(User user, TokenType tokenType, Duration lifetime);

}

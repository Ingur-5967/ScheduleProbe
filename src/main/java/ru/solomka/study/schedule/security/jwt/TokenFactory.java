package ru.solomka.study.schedule.security.jwt;

import ru.solomka.study.schedule.api.model.security.User;

import java.time.Duration;

public interface TokenFactory {

    /**
     * Генерирует JWT-токен.
     *
     * @param user сущность пользователя
     * @param tokenType тип токена (ACCESS, REFRESH)
     * @param lifetime время жизни токена от текущего момента
     * @return JWT-токен
     */
    String generateToken(User user, TokenType tokenType, Duration lifetime);

}

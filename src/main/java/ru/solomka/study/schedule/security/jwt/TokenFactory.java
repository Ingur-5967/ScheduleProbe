package ru.solomka.study.schedule.security.jwt;

import org.springframework.data.util.Pair;
import ru.solomka.study.schedule.api.model.user.User;

import java.time.Duration;

public interface TokenFactory {

    /**
     * Генерирует JWT-токен
     *
     * @param user сущность пользователя
     * @param tokenType тип токена (ACCESS, REFRESH)
     * @param lifetime время жизни токена от текущего момента
     * @return JWT-токен и его представление
     */
    Pair<String, TokenEntity> generateToken(User user, TokenType tokenType, Duration lifetime);

}

package ru.solomka.study.schedule.security.jwt;

import java.util.Optional;

public interface TokenParser {

    /**
     * Парсит и валидирует JWT-токен.
     *
     * @param token токен без Bearer
     * @return TokenEntity если токен валидный, Optional.empty() если нет
     */
    Optional<TokenEntity> parseAndValidateToken(String token);

}

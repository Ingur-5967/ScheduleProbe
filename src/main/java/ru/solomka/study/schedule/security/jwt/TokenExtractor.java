package ru.solomka.study.schedule.security.jwt;

public interface TokenExtractor {

    TokenEntity extract(String token);
}

package ru.solomka.study.schedule.security.jwt;

public interface TokenValidator {

    boolean validateToken(String token);

}

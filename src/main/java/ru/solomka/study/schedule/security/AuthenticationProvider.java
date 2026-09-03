package ru.solomka.study.schedule.security;

import ru.solomka.study.schedule.security.jwt.TokenEntity;

public interface AuthenticationProvider {

    void authenticate(TokenEntity user);

    ScheduleUserDetail getCurrentAuthenticatedUser();
}

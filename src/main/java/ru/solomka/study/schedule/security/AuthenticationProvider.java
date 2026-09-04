package ru.solomka.study.schedule.security;

public interface AuthenticationProvider {

    void authenticate(ScheduleUserDetail user);

    ScheduleUserDetail getCurrentAuthenticatedUser();
}

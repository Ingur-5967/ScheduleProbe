package ru.solomka.study.schedule.security;

public interface AuthenticationProvider {

    /**
     * Устанавливает в SecurityContextHolder объект пользователя
     * @param user сущность пользователя
     */
    void authenticate(ScheduleUserDetail user);

    /**
     * Возвращает текущий объект в SecurityContextHolder - Principal
     * @return ScheduleUserDetail представление пользователя
     */
    ScheduleUserDetail getCurrentAuthenticatedUser();
}

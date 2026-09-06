package ru.solomka.study.schedule.security.jwt;

import lombok.NonNull;
import ru.solomka.study.schedule.api.model.user.UserRole;

import java.time.Instant;

public record TokenEntity(@NonNull Long id, @NonNull String username,
                          @NonNull UserRole role, @NonNull TokenType type,
                          @NonNull Instant expiredAt) {}

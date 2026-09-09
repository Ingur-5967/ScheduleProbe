package ru.solomka.study.schedule.security.jwt;

import lombok.NonNull;
import ru.solomka.study.schedule.api.model.user.UserRole;

import java.time.Instant;
import java.util.UUID;

public record TokenEntity(@NonNull UUID id, @NonNull Long userId, @NonNull String username,
                          @NonNull UserRole role, @NonNull TokenType type,
                          @NonNull Instant expiredAt) {}

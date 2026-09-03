package ru.solomka.study.schedule.security.jwt;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.security.UserRole;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record TokenEntity(@NonNull String username, @NonNull UserRole role, @NonNull TokenType type,
                          @NonNull Instant expiredAt) {

}

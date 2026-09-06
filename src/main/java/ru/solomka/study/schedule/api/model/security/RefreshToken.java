package ru.solomka.study.schedule.api.model.security;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import ru.solomka.study.schedule.common.Identifiable;

import java.time.Instant;
import java.util.UUID;

public record RefreshToken(@Nullable UUID id, @NonNull Long userId,
                           @NonNull String token, @NonNull Instant expiredAt) implements Identifiable<UUID> {
    @Override
    public UUID getId() {
        return this.id;
    }
}

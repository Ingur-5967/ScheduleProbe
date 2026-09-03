package ru.solomka.study.schedule.api.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record User(@JsonIgnore @NonNull UUID id, @NonNull String username,
                   @JsonIgnore @NonNull String passwordHash,
                   @NonNull UserRole role, @NonNull Instant createdAt) {}

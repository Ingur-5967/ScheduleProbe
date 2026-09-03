package ru.solomka.study.schedule.api.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

public record User(@JsonIgnore @NonNull UUID id, @NonNull String username,
                   @JsonIgnore @NonNull String passwordHash,
                   @NonNull UserRole role, @NonNull Instant createdAt) {}

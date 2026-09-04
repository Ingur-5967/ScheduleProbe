package ru.solomka.study.schedule.api.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

public record User(@JsonIgnore @NonNull UUID id, @NonNull String username,
                   @JsonIgnore String passwordHash,
                   @NonNull UserRole role, Instant createdAt) {

    public User(UUID id, String username, UserRole role, Instant createdAt) {
        this(id, username, null, role, createdAt);
    }

    public User(UUID id, String username, UserRole role) {
        this(id, username, null, role, null);
    }

}

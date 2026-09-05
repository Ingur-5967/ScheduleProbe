package ru.solomka.study.schedule.api.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import ru.solomka.study.schedule.api.model.UserAdditionalInfo;

import java.time.Instant;
import java.util.UUID;

public record User(@JsonIgnore Long id, @NonNull String username,
                   @JsonIgnore String passwordHash, UserAdditionalInfo additionalInfo,
                   @NonNull UserRole role, Instant createdAt) {

    public User(Long id, String username, UserAdditionalInfo additionalInfo, UserRole role, Instant createdAt) {
        this(id, username, null, additionalInfo, role, createdAt);
    }

    public User(String username, UserAdditionalInfo additionalInfo, UserRole role, Instant createdAt) {
        this(null, username, null, additionalInfo, role, createdAt);
    }

    public User(String username, String passwordHash, UserAdditionalInfo additionalInfo, UserRole role, Instant createdAt) {
        this(null, username, passwordHash, additionalInfo, role, createdAt);
    }

    public User(String username, String passwordHash, UserRole role, Instant createdAt) {
        this(null, username, passwordHash, null, role, createdAt);
    }

    public User(Long id, String username, UserAdditionalInfo additionalInfo, UserRole role) {
        this(id, username, null, additionalInfo, role, null);
    }

    public User(Long id, String username, UserRole role) {
        this(id, username, null, null, role, null);
    }

    public User(Long id, String username, String passwordHash, UserRole role, Instant createdAt) {
        this(id, username, passwordHash, null, role, createdAt);
    }
}

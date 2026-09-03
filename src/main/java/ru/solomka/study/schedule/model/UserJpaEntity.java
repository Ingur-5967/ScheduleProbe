package ru.solomka.study.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.security.UserRole;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "soc", name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @Column(name = "role", nullable = false)
    UserRole role;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;
}

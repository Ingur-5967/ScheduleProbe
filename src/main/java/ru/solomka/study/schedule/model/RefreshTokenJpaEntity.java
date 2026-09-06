package ru.solomka.study.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.common.Identifiable;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenJpaEntity implements Identifiable<UUID> {

    @Id
    UUID id;

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "token", nullable = false)
    String token;

    @Column(name = "expired_at", nullable = false)
    Instant expiredAt;
}

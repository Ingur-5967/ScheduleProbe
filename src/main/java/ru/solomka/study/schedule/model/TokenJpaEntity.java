package ru.solomka.study.schedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "soc", name = "refresh_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenJpaEntity {

    @Id
    UUID id;

    @Column(name = "refresh_token", nullable = false)
    String refreshToken;

    @Column(name = "expired_at", nullable = false)
    Instant expiredAt;
}

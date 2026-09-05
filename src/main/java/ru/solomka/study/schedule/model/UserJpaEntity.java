package ru.solomka.study.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.security.UserRole;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(
            name = "users_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    Long id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_user_additional_info"))
    UserAdditionalInfoJpaEntity additionalInfo;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    UserRole role;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;
}

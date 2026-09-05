package ru.solomka.study.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "lesson_timetags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonTimeTagJpaEntity {

    @Id
    Long id;

    @Column(name = "comment", nullable = false)
    String comment;

    @Column(name = "expired_at", nullable = false)
    Instant expiredAt;
}

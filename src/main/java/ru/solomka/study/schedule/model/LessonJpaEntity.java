package ru.solomka.study.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.lesson.LessonType;

import java.time.Instant;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessons_seq")
    @SequenceGenerator(
            name = "lessons_seq",
            sequenceName = "lessons_id_seq",
            allocationSize = 1
    )
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "lesson_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    LessonType type;

    @Column(name = "teacher_id", nullable = false)
    Long teacherId;

    @Column(name = "room_id", nullable = false)
    String roomId;

    @Column(name = "group_id", nullable = false)
    String groupId;

    @Column(name = "day_of_week")
    Integer dayOfWeek;

    @Column(name = "is_upper_week")
    boolean isUpperWeek;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "fk_lesson_time_tag"))
    LessonTimeTagJpaEntity tag;

    @Column(name = "start_time", nullable = false)
    Instant startTime;

    @Column(name = "end_time", nullable = false)
    Instant endTime;
}

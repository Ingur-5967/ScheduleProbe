package ru.solomka.study.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.AssessmentType;

import java.util.UUID;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssessmentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessons_seq")
    @SequenceGenerator(
            name = "lessons_seq",
            sequenceName = "lessons_id_seq",
            allocationSize = 1
    )
    Long id;

    @Column(name = "subject_id", nullable = false)
    Long subjectId;

    @Column(name = "teacher_id", nullable = false)
    Long teacherId;

    @Column(name = "assessment_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    AssessmentType type;

    @Column(name = "group_id", nullable = false)
    String groupId;

    @Column(name = "room_id", nullable = false)
    String roomId;

    @Column(name = "start_time", nullable = false)
    Long startTime;

    @Column(name = "end_time", nullable = false)
    Long endTime;
}

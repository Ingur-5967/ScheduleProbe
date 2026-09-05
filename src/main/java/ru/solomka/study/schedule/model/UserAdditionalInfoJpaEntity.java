package ru.solomka.study.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.solomka.study.schedule.api.model.LevelEducation;

@Entity
@Table(name = "user_additional_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAdditionalInfoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(name = "cathedra", nullable = false)
    String cathedra;

    @Column(name = "group_id")
    String groupId;

    @Column(name = "study_period")
    Integer studyPeriod;

    @Column(name = "level_of_education")
    @Enumerated(value = EnumType.STRING)
    LevelEducation level;
}

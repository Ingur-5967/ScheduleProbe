package ru.solomka.study.schedule.api.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Assessment {

    @NonNull Long subjectId;

    @NonNull Long teacherId;

    @NonNull AssessmentType type;

    @NonNull String groupId;

    @NonNull String roomId;

    @NonNull Long startTime;

    @NonNull Long endTime;
}

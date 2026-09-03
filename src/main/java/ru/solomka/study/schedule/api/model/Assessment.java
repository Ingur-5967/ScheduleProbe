package ru.solomka.study.schedule.api.model;

import lombok.NonNull;

public record Assessment(@NonNull Long subjectId, @NonNull Long teacherId, @NonNull AssessmentType type,
                         @NonNull String groupId, @NonNull String roomId, @NonNull Long startTime,
                         @NonNull Long endTime) {}

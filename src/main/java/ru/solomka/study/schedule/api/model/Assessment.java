package ru.solomka.study.schedule.api.model;

import lombok.NonNull;

import java.time.Instant;

public record Assessment(@NonNull Long subjectId, @NonNull Long teacherId, @NonNull AssessmentType type,
                         @NonNull String groupId, @NonNull String roomId,
                         @NonNull Instant startTime, @NonNull Instant endTime) {}

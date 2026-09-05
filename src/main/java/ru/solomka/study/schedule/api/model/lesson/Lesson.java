package ru.solomka.study.schedule.api.model.lesson;

import lombok.NonNull;

import java.time.Instant;

public record Lesson(@NonNull String name, @NonNull LessonType type, @NonNull Long teacherId,
                     @NonNull String roomId, @NonNull String groupId, @NonNull Integer dayOfWeek, boolean isUpperWeek,
                     LessonTimeTag tag, @NonNull Instant startTime, @NonNull Instant endTime) {}
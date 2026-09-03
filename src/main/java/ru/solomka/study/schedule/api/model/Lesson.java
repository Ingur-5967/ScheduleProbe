package ru.solomka.study.schedule.api.model;

import lombok.NonNull;

public record Lesson(@NonNull String name, @NonNull LessonType type, @NonNull String teacherName,
                     @NonNull String roomId, @NonNull String groupId, @NonNull Integer dayOfWeek, boolean isUpperWeek,
                     @NonNull Long startTime, @NonNull Long endTime) {}
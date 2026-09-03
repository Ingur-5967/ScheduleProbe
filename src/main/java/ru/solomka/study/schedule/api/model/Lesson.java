package ru.solomka.study.schedule.api.model;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record Lesson(@NonNull String name, @NonNull LessonType type, @NonNull String teacherName,
                     @NonNull String roomId, @NonNull String groupId, @NonNull Integer dayOfWeek, boolean isUpperWeek,
                     @NonNull Long startTime, @NonNull Long endTime) {

}
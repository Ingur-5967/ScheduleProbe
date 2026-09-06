package ru.solomka.study.schedule.api.model.lesson;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.time.LocalTime;

public record Lesson(@Nullable Long id, @NonNull String name, @NonNull LessonType type,
                     @NonNull Long teacherId, @NonNull String roomId,
                     @NonNull String groupId, @NonNull Integer dayOfWeek,
                     boolean isUpperWeek, LessonTimeTag tag,
                     @NonNull LocalTime startTime, @NonNull LocalTime endTime) {}

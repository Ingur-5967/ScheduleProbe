package ru.solomka.study.schedule.api.model;

import lombok.NonNull;
import ru.solomka.study.schedule.api.model.lesson.LessonType;

import java.time.Instant;

public record ScheduleItem(@NonNull String lessonName, @NonNull LessonType lessonType,
                           @NonNull String roomId, @NonNull Long teacherId,
                           @NonNull Instant startTime, @NonNull Instant endTime) {}

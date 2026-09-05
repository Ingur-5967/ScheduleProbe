package ru.solomka.study.schedule.api.model;

import lombok.NonNull;
import ru.solomka.study.schedule.api.model.lesson.LessonType;

public record ScheduleItem(@NonNull String lessonName, @NonNull LessonType lessonType,
                           @NonNull String roomId, @NonNull Long teacherId,
                           @NonNull Long startTime, @NonNull Long endTime) {}

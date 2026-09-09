package ru.solomka.study.schedule.api.model.lesson;

import jakarta.annotation.Nullable;
import lombok.NonNull;

import java.time.LocalTime;

public record ScheduleItem(@Nullable Long id, @NonNull String lessonName, @NonNull LessonType lessonType,
                           @NonNull String roomId, @NonNull Long teacherId, LessonTimeTag timeTag,
                           @NonNull LocalTime startTime, @NonNull LocalTime endTime) {}

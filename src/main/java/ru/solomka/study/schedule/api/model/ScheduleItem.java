package ru.solomka.study.schedule.api.model;

import lombok.NonNull;

public record ScheduleItem(@NonNull String lessonName, @NonNull String roomId, @NonNull String teacherName,
                           @NonNull Long startTime, @NonNull Long endTime) {}

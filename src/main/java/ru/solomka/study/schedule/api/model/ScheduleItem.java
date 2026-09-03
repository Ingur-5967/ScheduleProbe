package ru.solomka.study.schedule.api.model;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record ScheduleItem(@NonNull String lessonName, @NonNull String roomId, @NonNull String teacherName,
                           @NonNull Long startTime, @NonNull Long endTime) {

}

package ru.solomka.study.schedule.api.model;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record ScheduleInfo(@NonNull Integer dayOfWeek, @NonNull List<ScheduleItem> dayScheduleDetail) {

}

package ru.solomka.study.schedule.api.model;

import lombok.NonNull;

import java.util.List;

public record ScheduleInfo(@NonNull Integer dayOfWeek, @NonNull List<ScheduleItem> dayScheduleDetail) {}

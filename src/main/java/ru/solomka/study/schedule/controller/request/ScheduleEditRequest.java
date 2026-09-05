package ru.solomka.study.schedule.controller.request;

import ru.solomka.study.schedule.api.model.ScheduleInfo;

import java.util.List;

public record ScheduleEditRequest(List<ScheduleInfo> scheduleInfo) {}

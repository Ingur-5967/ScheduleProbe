package ru.solomka.study.schedule.controller.request;

import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;

import java.util.List;

public record ScheduleTagSetRequest(List<LessonTimeTag> timeTags) {}

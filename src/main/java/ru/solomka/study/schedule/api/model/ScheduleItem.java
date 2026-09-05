package ru.solomka.study.schedule.api.model;

import lombok.NonNull;
import ru.solomka.study.schedule.api.model.lesson.LessonTimeTag;
import ru.solomka.study.schedule.api.model.lesson.LessonType;

import java.time.LocalTime;

public record ScheduleItem(Long id, @NonNull String lessonName, @NonNull LessonType lessonType,
                           @NonNull String roomId, @NonNull Long teacherId, LessonTimeTag timeTag,
                           @NonNull LocalTime startTime, @NonNull LocalTime endTime) {}

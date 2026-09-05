package ru.solomka.study.schedule.api.model.lesson;

import lombok.NonNull;

import java.time.Instant;

public record LessonTimeTag(@NonNull Long lessonId, @NonNull String message, @NonNull Instant expiredAt) {}
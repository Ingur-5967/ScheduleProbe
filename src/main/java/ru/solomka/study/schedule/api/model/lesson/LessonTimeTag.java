package ru.solomka.study.schedule.api.model.lesson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

import java.time.Instant;

public record LessonTimeTag(@JsonIgnore @NonNull Long id, @NonNull String comment, @NonNull Instant expiredAt) {}
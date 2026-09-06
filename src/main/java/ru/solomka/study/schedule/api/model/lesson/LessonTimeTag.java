package ru.solomka.study.schedule.api.model.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.time.Instant;

public record LessonTimeTag(@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @NonNull Long id,
                            @NonNull String comment, @NonNull Instant expiredAt) {}
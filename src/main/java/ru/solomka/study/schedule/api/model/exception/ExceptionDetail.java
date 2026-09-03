package ru.solomka.study.schedule.api.model.exception;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public record ExceptionDetail(int code, @NonNull String message) {}
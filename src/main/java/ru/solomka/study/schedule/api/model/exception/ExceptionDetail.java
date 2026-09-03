package ru.solomka.study.schedule.api.model.exception;

import lombok.NonNull;

public record ExceptionDetail(int code, @NonNull String message) {}
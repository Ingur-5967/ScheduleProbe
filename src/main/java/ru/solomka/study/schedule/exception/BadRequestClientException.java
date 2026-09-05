package ru.solomka.study.schedule.exception;

public class BadRequestClientException extends RuntimeException {
    public BadRequestClientException(String message) {
        super(message);
    }
}

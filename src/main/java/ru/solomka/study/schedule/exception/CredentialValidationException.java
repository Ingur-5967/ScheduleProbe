package ru.solomka.study.schedule.exception;

public class CredentialValidationException extends RuntimeException {
    public CredentialValidationException(String message) {
        super(message);
    }
}

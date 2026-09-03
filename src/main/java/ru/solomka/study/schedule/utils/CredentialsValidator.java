package ru.solomka.study.schedule.utils;

import org.springframework.stereotype.Component;
import ru.solomka.study.schedule.exception.CredentialValidationException;

import java.util.regex.Pattern;

@Component
public class CredentialsValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;

    public void validateCredentials(String username, String password, boolean emailValidation) {
        validateUsername(username, emailValidation);
        validatePassword(password);
    }

    public void validateUsername(String username, boolean emailValidation) {
        if (username == null || username.isBlank()) {
            throw new CredentialValidationException("Имя пользователя не может быть пустым");
        }

        if (username.length() > 50) {
            throw new CredentialValidationException("Имя пользователя слишком длинное");
        }

        if (emailValidation && !EMAIL_PATTERN.matcher(username).matches()) {
            throw new CredentialValidationException("Некорректный формат Email");
        }

        if(containsInUsernameForbiddenSymbols(username)) {
            throw new CredentialValidationException("Имя пользователя содержит запрещенные символы");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new CredentialValidationException("Пароль не может быть пустым");
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new CredentialValidationException("Пароль должен содержать минимум " + MIN_PASSWORD_LENGTH + " символов");
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new CredentialValidationException("Пароль слишком длинный");
        }

        if (password.contains(" ")) {
            throw new CredentialValidationException("Пароль не может содержать пробелы");
        }

        if (!password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
            throw new CredentialValidationException("Пароль должен содержать как буквы, так и цифры");
        }
    }

    public boolean containsInUsernameForbiddenSymbols(String username) {
        return username.contains("'") || username.contains("\"") || username.contains(";");
    }
}

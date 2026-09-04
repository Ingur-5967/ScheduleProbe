package ru.solomka.study.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.solomka.study.schedule.api.model.exception.ExceptionDetail;

@ControllerAdvice
public class ExceptionAdviceController {

    // 401

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ExceptionDetail> exceptionAccessDeniedDetail(AccessDeniedException accessDeniedException) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.UNAUTHORIZED.value(), accessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detail);
    }

    @ExceptionHandler(value = TokenPayloadExtractException.class)
    public ResponseEntity<ExceptionDetail> exceptionTokenPayloadExtractDetail(TokenPayloadExtractException tokenPayloadExtractException) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.UNAUTHORIZED.value(), tokenPayloadExtractException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detail);
    }

    // 404

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDetail> exceptionUserNotFoundDetail(UserNotFoundException userNotFoundException) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.NOT_FOUND.value(), userNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    // 409

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionDetail> exceptionUserAlreadyExistsDetail(UserAlreadyExistsException userAlreadyExistsException) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.CONFLICT.value(), userAlreadyExistsException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(detail);
    }

    // 403

    @ExceptionHandler(value = CredentialValidationException.class)
    public ResponseEntity<ExceptionDetail> exceptionCredentialsValidationDetail(CredentialValidationException credentialValidationException) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.BAD_REQUEST.value(), credentialValidationException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ExceptionDetail> exceptionAuthenticationDetail(AuthenticationException authenticationException) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.BAD_REQUEST.value(), authenticationException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    @ExceptionHandler(value = BadRequestClientExceptiom.class)
    public ResponseEntity<ExceptionDetail> exceptionAuthenticationDetail(BadRequestClientExceptiom badRequestClientExceptiom) {
        ExceptionDetail detail = new ExceptionDetail(HttpStatus.BAD_REQUEST.value(), badRequestClientExceptiom.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }
}

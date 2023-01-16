package com.coursework.car.exceptions;

import com.coursework.car.controller.MessageController;
import com.coursework.car.controller.OfferController;
import com.coursework.car.controller.ReservationController;
import com.coursework.car.controller.UserController;
import com.coursework.car.validation.ValidationErrorResponse;
import com.coursework.car.validation.Violation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice(assignableTypes = { UserController.class, OfferController.class, ReservationController.class, MessageController.class })
public class CustomExceptionHandler {

    private final Logger LOGGER = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleUnknownException(Exception exception) {
        logError(exception);
        return buildErrorResponse(INTERNAL_SERVER_ERROR, "Something went wrong. Try again later");
    }

    @ExceptionHandler(value = { OfferNotFoundException.class, ReservationNotFoundException.class, UserNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(Exception exception) {
        logError(exception);
        return buildErrorResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(value = { EmailExistsException.class })
    public ValidationErrorResponse handleEmailExistsException(EmailExistsException exception) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        Violation violation = new Violation("email", exception.getMessage());
        error.setViolations(Collections.singletonList(violation));
        logError(exception);
        return error;
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleForbiddenException(Exception exception) {
        logError(exception);
        return buildErrorResponse(FORBIDDEN, exception);
    }

    @ExceptionHandler(value = { BadCredentialsException.class })
    public ResponseEntity<Object> handleCredentialsException(Exception exception) {
        logError(exception);
        return buildErrorResponse(UNAUTHORIZED, exception);
    }

    public static ResponseEntity<Object> buildErrorResponse(HttpStatus status, Exception exception) {
        return buildErrorResponse(status, exception.getMessage());
    }

    public static ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    @ExceptionHandler(value = { ConstraintViolationException.class } )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    private void logError(Exception exception) {
        LOGGER.error("Exception", exception);
//        exception.printStackTrace();
    }
}

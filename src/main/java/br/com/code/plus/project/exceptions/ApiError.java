package br.com.code.plus.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class ApiError {

    private Integer statusCode;
    private String error;
    private List<String> messages;

    private ApiError(Integer statusCode, String error, String message) {
        this(statusCode, error,
                Objects.nonNull(message) ? singletonList(message) : Collections.emptyList());
    }

    private ApiError(Integer statusCode, String error, List<String> messages) {
        this.statusCode = statusCode;
        this.error = error;
        this.messages = messages;
    }

    public static ApiError fromHttpError(HttpStatus httpStatus, Exception exception) {
        return fromHttpError(httpStatus, exception, httpStatus.getReasonPhrase());
    }

    public static ApiError fromHttpError(HttpStatus httpStatus, Exception exception,
                                         String errorIdentifier) {
        return new ApiError(httpStatus.value(), errorIdentifier, exception.getMessage());
    }

    public static ApiError fromMessage(HttpStatus httpStatus, String message) {
        return new ApiError(httpStatus.value(), httpStatus.getReasonPhrase(), message);
    }

    public static ApiError fromMessage(HttpStatus httpStatus, List<String> message) {
        return new ApiError(httpStatus.value(), httpStatus.getReasonPhrase(), message);
    }

    public static ApiError fromBindingResult(BindingResult bindingResult) {
        List<String> erros = bindingResult
                .getAllErrors()
                .stream()
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return fieldError.getField() + " : " + fieldError.getDefaultMessage();
                })
                .collect(Collectors.toList());

        return new ApiError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), erros);

    }

    public String getError() {
        return error;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}

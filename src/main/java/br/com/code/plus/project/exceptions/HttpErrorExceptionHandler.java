package br.com.code.plus.project.exceptions;
import javax.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
    @ControllerAdvice
    @Slf4j
    public class HttpErrorExceptionHandler extends ResponseEntityExceptionHandler {

        private static final Logger LOGGER = LoggerFactory.getLogger(HttpErrorExceptionHandler.class);

        @ResponseStatus(UNPROCESSABLE_ENTITY)
        @ExceptionHandler(DomainBusinessException.class)
        @ResponseBody
        public ApiError treatBusinessError(DomainBusinessException e) {
            log.error("BUSINESS ERROR" + e.getMessage(), e);
            return ApiError.fromHttpError(UNPROCESSABLE_ENTITY, e);
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
        @ResponseBody
        public ApiError treatErrorResourceNotFound(RuntimeException e) {
            log.error("RESOURCE NOT FOUND ERROR" + e.getMessage(), e);
            return ApiError.fromHttpError(NOT_FOUND, e);
        }

        @ResponseStatus(INTERNAL_SERVER_ERROR)
        @ExceptionHandler(Exception.class)
        @ResponseBody
        public ApiError handleUnexpectedError(Exception e) {
            log.error("UNEXPECTED ERROR: " + e.getMessage(), e);
            return ApiError.fromMessage(INTERNAL_SERVER_ERROR, "Unexpected server error.");
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(ForbiddenException.class)
        @ResponseBody
        public ApiError handleErrorPermission(ForbiddenException e) {
            log.error("PERMISSION ERROR" + e.getMessage(), e);
            return ApiError.fromHttpError(HttpStatus.FORBIDDEN, e);
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @ExceptionHandler(AccessDeniedException.class)
        @ResponseBody
        public ApiError handleAccessForbidden(AccessDeniedException e) {
            log.error("PERMISSION ERROR" + e.getMessage(), e);
            return ApiError.fromHttpError(HttpStatus.FORBIDDEN, e);
        }

        @Override
        protected ResponseEntity handleExceptionInternal(
                Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

            LOGGER.error("INVALID PARAMETER ERROR REQUEST IN (GENERIC HANDLER): " + ex.getMessage());
            if (ex instanceof MethodArgumentNotValidException) {
                List<String> message = handleMissingParameterException((MethodArgumentNotValidException) ex);
                return new ResponseEntity<>(ApiError.fromMessage(BAD_REQUEST, message), headers, status);
            }
            return new ResponseEntity<>(ApiError.fromHttpError(status, ex), headers, status);
        }

        private List<String> handleMissingParameterException(MethodArgumentNotValidException ex) {

            return ex.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
        }
}

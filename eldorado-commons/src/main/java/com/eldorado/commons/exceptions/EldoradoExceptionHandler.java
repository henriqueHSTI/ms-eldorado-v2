package com.eldorado.commons.exceptions;

import com.eldorado.commons.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class EldoradoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex,
                                                          WebRequest request) {
        CustomErrorResponse customErrorResponse =
                CustomErrorResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(ex.getLocalizedMessage())
                        .errors(List.of(ex.getLocalizedMessage()))
                        .build();

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(UnauthorizedException ex,
                                                            WebRequest request) {
        CustomErrorResponse customErrorResponse =
                CustomErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getLocalizedMessage())
                        .errors(List.of(ex.getLocalizedMessage()))
                        .build();

        return ResponseEntity.badRequest().body(customErrorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleExceptionInternal(UnauthorizedException ex,
                                                          WebRequest request) {
        CustomErrorResponse customErrorResponse =
                CustomErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message(ex.getLocalizedMessage())
                        .errors(List.of(ex.getLocalizedMessage()))
                        .build();

        return new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
    }
}

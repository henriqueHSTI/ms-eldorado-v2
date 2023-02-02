package com.eldorado.commons.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(final Throwable cause) {
        super(cause);
    }
}

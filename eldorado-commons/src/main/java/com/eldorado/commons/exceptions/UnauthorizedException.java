package com.eldorado.commons.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(final String message) {
        super(message);
    }

    public UnauthorizedException(final String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
}

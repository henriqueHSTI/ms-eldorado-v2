package com.eldorado.commons.exceptions;

public class NicolayException extends RuntimeException {

    public NicolayException(final String message) {
        super(message);
    }

    public NicolayException(final String message, Throwable cause) {
        super(message, cause);
    }

    public NicolayException(Throwable cause) {
        super(cause);
    }
}

package ru.melonhell.violenceuniverse.server.exceptions;

public abstract class ViolenceUniverseException extends RuntimeException {
    public ViolenceUniverseException() {
    }

    public ViolenceUniverseException(String message) {
        super(message);
    }

    public ViolenceUniverseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViolenceUniverseException(Throwable cause) {
        super(cause);
    }

    public ViolenceUniverseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package org.Darwyi.CollectionTypes.exceptions;

public class LimitException extends RuntimeException {
    public LimitException(String message) {
        super("Limit: " + message);
    }

    public LimitException() {
        super("Limit");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

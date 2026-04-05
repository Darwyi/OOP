package org.Darwyi.CollectionTypes.exceptions;

public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException(String message) {
        super(message);
    }
    public InvalidIndexException() {
        super();
    }

    @Override
    public String getMessage() {
        String s = super.getMessage();

        return "Index out of bound: " + s;
    }
}

package org.Darwyi.CollectionTypes.exceptions;

public class ValueIsNull extends Exception {
    public ValueIsNull(String message) {
        super(message);
    }
    public ValueIsNull() {
        super();
    }

    @Override
    public String getMessage() {
        String s = super.getMessage();
        return "Value is null: " + s;
    }
}

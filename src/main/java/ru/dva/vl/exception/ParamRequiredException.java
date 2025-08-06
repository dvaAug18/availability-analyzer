package ru.dva.vl.exception;

public class ParamRequiredException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Not found required param [%s]";

    public ParamRequiredException(String paramName) {
        super(String.format(MESSAGE_FORMAT, paramName));
    }

    public ParamRequiredException(String paramName, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, paramName), cause);
    }

}

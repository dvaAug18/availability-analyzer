package ru.dva.vl.exception;

public class ParamFormatException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Param [%s] has incorrect format";

    public ParamFormatException(String paramName) {
        super(String.format(MESSAGE_FORMAT, paramName));
    }

    public ParamFormatException(String paramName, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, paramName), cause);
    }

}

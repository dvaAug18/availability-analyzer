package ru.dva.vl.exception;

public class RowParsingException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Log row [%s] has incorrect format";

    public RowParsingException(String row) {
        super(String.format(MESSAGE_FORMAT, row));
    }

    public RowParsingException(String row, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, row), cause);
    }

}

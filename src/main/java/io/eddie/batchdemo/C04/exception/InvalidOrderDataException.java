package io.eddie.batchdemo.C04.exception;

import lombok.Getter;

@Getter
public class InvalidOrderDataException extends RuntimeException {

    private final int lineNumber;
    private final String rawLine;
    private final String reason;

    public InvalidOrderDataException(int lineNumber, String rawLine, String reason) {
        super(reason);
        this.reason = reason;
        this.lineNumber = lineNumber;
        this.rawLine = rawLine;
    }

    public InvalidOrderDataException(int lineNumber, String rawLine, String reason, Throwable cause) {
        super(reason, cause);
        this.reason = reason;
        this.lineNumber = lineNumber;
        this.rawLine = rawLine;
    }

}

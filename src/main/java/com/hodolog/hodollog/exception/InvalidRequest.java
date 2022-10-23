package com.hodolog.hodollog.exception;


import lombok.Getter;

/**
 * 정책상 400
 */
@Getter
public class InvalidRequest extends BlogException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String message;

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public InvalidRequest(String message, Throwable cause) {
        super(MESSAGE, cause);
    }

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}

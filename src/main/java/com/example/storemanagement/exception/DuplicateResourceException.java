package com.example.storemanagement.exception;

public class DuplicateResourceException extends ApplicationException {
    private final ExceptionCode exceptionCode;

    public DuplicateResourceException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}

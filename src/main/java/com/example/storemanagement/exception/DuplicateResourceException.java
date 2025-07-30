package com.example.storemanagement.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends ApplicationException {
    private final ExceptionCode exceptionCode;

    public DuplicateResourceException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}

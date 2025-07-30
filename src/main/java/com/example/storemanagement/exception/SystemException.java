package com.example.storemanagement.exception;

import lombok.Getter;

@Getter
public class SystemException extends ApplicationException {
    private final ExceptionCode exceptionCode;

    public SystemException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}

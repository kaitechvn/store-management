package com.example.storemanagement.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    /* ResourceNotFoundException */
    USER_NOT_FOUND("user-not-found", "User has not been found"),

    /* DuplicateResourceException */
    USER_EXISTED("user-existed", "User already existed"),
    EMAIL_EXISTED("email-existed", "email already exíted");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

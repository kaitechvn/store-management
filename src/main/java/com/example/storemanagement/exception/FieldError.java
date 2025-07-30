package com.example.storemanagement.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldError {
    protected String code;
    protected String message;
}

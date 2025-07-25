package com.example.storemanagement.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldError {
    private String code;
    private String message;
}

package com.example.storemanagement.exception;

import com.example.storemanagement.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> fieldErrors;

    public static ErrorResponse errorResponse(int status, String path, String message, List<FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .status(status)
                .path(path)
                .message(message)
                .timestamp(TimeUtils.getCurrentTime())
                .fieldErrors(fieldErrors)
                .build();
    }

    public static ErrorResponse errorResponse(int status, String path, String message) {
        return errorResponse(status, path, message, null);
    }
}

package com.example.storemanagement.exception.response;

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
    private S
    private String path;
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldError> fieldErrors;


    /**
     * Static factory method to quickly create ErrorResponse.
     */
    public static ErrorResponse errorResponse(int status, String path, List<FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .status(status)
                .path(path)
                .timestamp(TimeUtils.getCurrentTime())
                .fieldErrors(fieldErrors)
                .build();
    }

    /**
     * Overload: when no field errors.
     */
    public static ErrorResponse errorResponse(int status, String path) {
        return ErrorResponse.builder()
                .status(status)
                .path(path)
                .timestamp(TimeUtils.getCurrentTime())
                .build();
    }
}

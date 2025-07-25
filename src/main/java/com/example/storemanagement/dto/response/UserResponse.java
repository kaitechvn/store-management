package com.example.storemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> role;
}

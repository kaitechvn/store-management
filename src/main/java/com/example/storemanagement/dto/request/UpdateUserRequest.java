package com.example.storemanagement.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    UUID id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    List<UUID> roleIds;
}

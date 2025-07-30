package com.example.storemanagement.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.example.storemanagement.utils.Constant.DEFAULT_KEYCLOAK_PAGE;
import static com.example.storemanagement.utils.Constant.DEFAULT_KEYCLOAK_SIZE;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserRequest {

    String username;
    String email;
    String firstName;
    String lastName;
    Boolean enabled;
    String role;
    Integer page = DEFAULT_KEYCLOAK_PAGE;
    Integer size = DEFAULT_KEYCLOAK_SIZE;

}

package com.example.storemanagement.dto.request;

import com.example.storemanagement.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank(message = "{validation.username.required}")
    String username;
    
    @ValidPassword
    String password;
    
    String firstName;
    
    String lastName;
    
    @Email(message = "{validation.email.invalid}")
    @NotBlank(message = "{validation.email.required}")
    String email;
    
    List<UUID> roleIds;
}

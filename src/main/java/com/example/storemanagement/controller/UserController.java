package com.example.storemanagement.controller;

import com.example.storemanagement.dto.request.CreateUserRequest;
import com.example.storemanagement.dto.request.SearchUserRequest;
import com.example.storemanagement.dto.request.pagination.BasePagination;
import com.example.storemanagement.dto.response.UserResponse;
import com.example.storemanagement.service.UserService;
import com.example.storemanagement.utils.PagingUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PagingUtils pagingUtils;
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<BasePagination<UserResponse>> searchUser(SearchUserRequest request){

        BasePagination<UserResponse> response = userService.searchUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

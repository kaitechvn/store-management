package com.example.storemanagement.service;

import com.example.storemanagement.dto.request.CreateUserRequest;
import com.example.storemanagement.dto.request.SearchUserRequest;
import com.example.storemanagement.dto.request.UpdateUserRequest;
import com.example.storemanagement.dto.request.pagination.BasePagination;
import com.example.storemanagement.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(UpdateUserRequest request);

    BasePagination<UserResponse> searchUser(SearchUserRequest request);

    void deleteUser(UUID userId);
}

package com.example.storemanagement.service.impl;

import com.example.storemanagement.dto.request.CreateUserRequest;
import com.example.storemanagement.dto.request.UpdateUserRequest;
import com.example.storemanagement.dto.response.UserResponse;
import com.example.storemanagement.exception.DuplicateResourceException;
import com.example.storemanagement.exception.ExceptionCode;
import com.example.storemanagement.service.UserService;
import com.example.storemanagement.utils.KeycloakUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.storemanagement.exception.ExceptionCode.EMAIL_EXISTED;
import static com.example.storemanagement.exception.ExceptionCode.USER_EXISTED;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${keycloak.admin-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.app-realm}")
    private String realm;

    private final KeycloakUtils keycloakUtils;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        try {
            String accessToken = keycloakUtils.getAdminAccessToken();
            String userId = createKeycloakUser(request, accessToken);

            List<String> roleNames = new ArrayList<>();
            if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
                assignRolesToUser(userId, request.getRoleIds(), accessToken);
                roleNames = getRoleNames(request.getRoleIds(), accessToken);
            }

            return UserResponse.builder()
                .id(UUID.fromString(userId))
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(roleNames)
                .build();
        } catch (Exception e) {
            log.error("Failed to create user: {}", e.getMessage());
            throw parseKeycloakError(e);
        }
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(UUID userId) {
        // TODO document why this method is empty
    }

    private String createKeycloakUser(CreateUserRequest request, String accessToken) throws JsonProcessingException {
        String userUrl = keycloakUrl + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", request.getUsername());
        userMap.put("email", request.getEmail());
        userMap.put("firstName", request.getFirstName());
        userMap.put("lastName", request.getLastName());
        userMap.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", request.getPassword());
        credentials.put("temporary", false);
        userMap.put("credentials", List.of(credentials));

        String userJson = objectMapper.writeValueAsString(userMap);
        HttpEntity<String> entity = new HttpEntity<>(userJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(userUrl, entity, String.class);

        // Keycloak returns the created user's URL in Location header
        String createdUserUrl = Objects.requireNonNull(response.getHeaders().getLocation()).toString();
        // Extract userId from the end of the URL
        return createdUserUrl.substring(createdUserUrl.lastIndexOf('/') + 1);
    }

    private void assignRolesToUser(String userId, List<UUID> roleIds, String accessToken) throws JsonProcessingException {
        String roleUrl = keycloakUrl + realm + "/users/" + userId + "/role-mappings/realm";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        List<Map<String, Object>> rolesArray = new ArrayList<>();
        for (UUID roleId : roleIds) {
            Map<String, Object> roleDetails = getRoleById(roleId, accessToken);
            rolesArray.add(roleDetails);
        }

        String rolesJson = objectMapper.writeValueAsString(rolesArray);
        HttpEntity<String> entity = new HttpEntity<>(rolesJson, headers);

        restTemplate.postForEntity(roleUrl, entity, String.class);
    }

    private List<String> getRoleNames(List<UUID> roleIds, String accessToken) {
        String rolesUrl = keycloakUrl + realm + "/roles";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(rolesUrl, HttpMethod.GET, entity, List.class);
        List<Map<String, Object>> roles = Objects.requireNonNull(response.getBody());

        return roleIds.stream()
            .map(roleId -> {
                for (Map<String, Object> role : roles) {
                    if (roleId.toString().equals(role.get("id"))) {
                        return (String) role.get("name");
                    }
                }
                return null;
            })
            .filter(Objects::nonNull)
            .toList();
    }

    private Map getRoleById(UUID roleId, String accessToken) {
        String roleByIdUrl = keycloakUrl + realm + "/roles-by-id/" + roleId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(roleByIdUrl, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

    private RuntimeException parseKeycloakError(Exception e) {
        String errorMessage = e.getMessage().toLowerCase();
        
        if (errorMessage.contains("user exists") || errorMessage.contains("username")) {
            return new com.example.storemanagement.exception.DuplicateResourceException(USER_EXISTED);
        }
        
        if (errorMessage.contains("email") && errorMessage.contains("exists")) {
            return new com.example.storemanagement.exception.DuplicateResourceException(EMAIL_EXISTED);
        }

        
        return null;
    }
}

package com.example.storemanagement.utils;

import com.example.storemanagement.dto.response.UserResponse;
import com.example.storemanagement.exception.SystemException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.storemanagement.exception.ExceptionCode.KEYCLOAK_FAILED;

@Component
public class KeycloakUtils {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.master-realm}")
    private String realm;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminAccessToken() {
        String tokenUrl = keycloakUrl + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", adminClientId);
        body.add("username", adminUsername);
        body.add("password", adminPassword);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(tokenUrl, request, JsonNode.class);

        return  Objects.requireNonNull(response.getBody()).get("access_token").asText();
    }

    public List<UserResponse> parseUsersFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(json, UserResponse[].class));
        } catch (JsonProcessingException e) {
            throw new SystemException(KEYCLOAK_FAILED);
        }
    }

}

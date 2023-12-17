package com.ransankul.priceaction.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ransankul.priceaction.model.AccessTokenResponse;
import com.ransankul.priceaction.model.UserApiMapping;
import com.ransankul.priceaction.service.UserApiMappingService;


@RestController
@RequestMapping("/api/upstox/auth")
public class UpstoxAuthController {

    @Autowired
    private UserApiMappingService userApiMappingService;

    @GetMapping("/gettoken")
    public ResponseEntity<String> getAccessToken(@RequestParam String code, @RequestParam long id) throws JsonMappingException, JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add("Api-Version", "2.0");

        UserApiMapping userApiMapping = userApiMappingService.findById(id);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("client_id", userApiMapping.getApikey());
        requestBody.add("client_secret", userApiMapping.getApisecret());
        requestBody.add("redirect_uri", userApiMapping.getRedirecturl());
        requestBody.add("grant_type", "authorization_code");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api-v2.upstox.com/login/authorization/token",
                requestBody,
                String.class,
                headers
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            AccessTokenResponse token = objectMapper.readValue(responseBody, AccessTokenResponse.class);

            String accessToken = token.getAccessToken();
            userApiMapping.setJwttoken(accessToken);
            userApiMapping.setConnectedOrNot(true);
            userApiMappingService.saveJWTTokenAPI(userApiMapping);
            return ResponseEntity.ok("201");
        } else {
            return ResponseEntity.status(response.getStatusCode())
                    .body(response.getStatusCode()+"");
        }
    }

   
}

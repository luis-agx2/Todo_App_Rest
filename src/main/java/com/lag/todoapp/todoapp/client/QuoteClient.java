package com.lag.todoapp.todoapp.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteClient {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public QuoteClient(RestTemplate restTemplate,
                       ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> T get(String url, Class<T> clazz) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                return objectMapper.readValue(response.getBody(), clazz);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return null;
    }
}

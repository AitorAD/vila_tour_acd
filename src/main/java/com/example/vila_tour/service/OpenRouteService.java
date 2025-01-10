package com.example.vila_tour.service;

import com.example.vila_tour.domain.RouteRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Service
public class OpenRouteService {

    @Value("${openrouteservice.api.key}")
    private String apiKey;

    @Value("${openrouteservice.api.url}")
    private String API_URL;

    private final RestTemplate restTemplate;

    public OpenRouteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JsonNode getRoute(RouteRequestDTO routeRequest) {

        String url = API_URL + routeRequest.getProfile();

        // Crear el cuerpo de la solicitud a partir del DTO
        Map<String, Object> body = Map.of(
                "coordinates", routeRequest.getCoordinates()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            // Realizar la solicitud a OpenRouteService
            ResponseEntity<JsonNode> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    JsonNode.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Manejar errores, por ejemplo, si la API devuelve un error
            e.printStackTrace();
            return null;
        }
    }
}

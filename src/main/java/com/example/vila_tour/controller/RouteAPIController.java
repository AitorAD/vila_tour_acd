package com.example.vila_tour.controller;

import com.example.vila_tour.domain.RouteRequestDTO;
import com.example.vila_tour.service.OpenRouteService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openRoutes")
public class RouteAPIController {

    private final OpenRouteService openRouteService;

    public RouteAPIController(OpenRouteService openRouteService) {
        this.openRouteService = openRouteService;
    }
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<JsonNode> createRoute(@RequestBody RouteRequestDTO request) {
        JsonNode routeData = openRouteService.getRoute(request);
        return ResponseEntity.ok(routeData);
    }

    /*
    Este es un ejemplo de Json Correcto para el PostMan
    {
      "coordinates": [
        [-0.2106, 38.5311],
        [-0.2115, 38.5230]
      ],
      "profile": "foot-walking"
    }
* */
}

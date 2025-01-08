package com.example.vila_tour.service;
import com.example.vila_tour.domain.Place;
import com.example.vila_tour.domain.Route;

import java.util.List;
import java.util.Optional;


public interface RouteService {
    List<Route> findAll();
    Optional<Route> findById(long id);

    List<Route> findByPlacesIn(List<Place> places);

    List<Route> findByNameContaining(String name);

    Route addRoute(Route route);
    Route modifyRoute(long id, Route newRoute);
    void deleteRoute(long id);
}












/*
    private final RestTemplate restTemplate;

    @Value("${openrouteservice.api.key}")
    private String apiKey;

    @Value("${openrouteservice.api.url}")
    private String apiUrl;

    // Modos válidos
    private static final Set<String> VALID_MODES = Set.of("driving-car", "foot-walking", "cycling-regular");

    public RouteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Petición con la ubicación actual del usuario y los lugares
    public String getDirectionsWithUserLocation(String mode, Coordinate userCoordinate, List<Place> places) {
        validateMode(mode);

        // Extraemos las coordenadas de los lugares y añadimos la posición del usuario
        List<List<Double>> coordinates = extractCoordinatesWithUserLocation(userCoordinate, places);

        String url = buildRequestUrl(mode);
        String body = buildRequestBody(coordinates);

        HttpHeaders headers = createHeaders();

        return sendRequest(url, body, headers);
    }

    // Petición sin la ubicación actual del usuario, solo con los lugares
    public String getDirectionsWithoutUserLocation(String mode, List<Place> places) {
        validateMode(mode);

        // Extraemos las coordenadas solo de los lugares
        List<List<Double>> coordinates = extractCoordinates(places);

        String url = buildRequestUrl(mode);
        String body = buildRequestBody(coordinates);

        HttpHeaders headers = createHeaders();

        return sendRequest(url, body, headers);
    }

    // Extrae las coordenadas de los lugares con la ubicación del usuario
    private List<List<Double>> extractCoordinatesWithUserLocation(Coordinate userCoordinate, List<Place> places) {
        // Agregamos la coordenada del usuario al principio de la lista
        List<List<Double>> coordinates = places.stream()
                .map(place -> {
                    Coordinate coordinate = place.getCoordinate();
                    return List.of(coordinate.getLatitude(), coordinate.getLongitude());
                })
                .collect(Collectors.toList());

        // Añadimos la ubicación del usuario al inicio
        coordinates.add(0, List.of(userCoordinate.getLatitude(), userCoordinate.getLongitude()));

        return coordinates;
    }

    // Extrae las coordenadas solo de los lugares
    private List<List<Double>> extractCoordinates(List<Place> places) {
        return places.stream()
                .map(place -> {
                    Coordinate coordinate = place.getCoordinate();
                    return List.of(coordinate.getLatitude(), coordinate.getLongitude());
                })
                .collect(Collectors.toList());
    }

    // Construcción de la URL para la solicitud
    private String buildRequestUrl(String mode) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .path(mode)
                .toUriString();
    }

    // Construcción del cuerpo de la solicitud
    private String buildRequestBody(List<List<Double>> coordinates) {
        return String.format("{\"coordinates\": %s}", coordinates.toString());
    }

    // Crear los encabezados
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        headers.set("Content-Type", "application/json");
        return headers;
    }

    // Enviar la solicitud y obtener la respuesta
    private String sendRequest(String url, String body, HttpHeaders headers) {
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return response.getBody();
    }

    // Validación del modo de transporte
    private void validateMode(String mode) {
        if (!VALID_MODES.contains(mode)) {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
    }*/


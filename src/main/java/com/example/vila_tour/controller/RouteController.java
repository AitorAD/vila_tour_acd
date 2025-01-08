package com.example.vila_tour.controller;

import com.example.vila_tour.domain.*;
import com.example.vila_tour.exception.RouteNotFoundException;
import com.example.vila_tour.security.services.AuthService;
import com.example.vila_tour.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controlador para Rutas
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/routes")
@RestController
@Tag(name = "Routes", description = "Catálogo de rutas")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private AuthService authService;

    // MÉTODOS GET

    @Operation(summary = "Obtiene el listado de rutas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de rutas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Route.class))))})
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<Route>> getRoutes() {
        List<Route> routes = routeService.findAll();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la ruta determinada por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta encontrada",
                    content = @Content(schema = @Schema(implementation = Route.class))),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Route> getRoute(@PathVariable long id, Authentication authentication) {
        Route route = routeService.findById(id)
                .orElseThrow(() -> new RouteNotFoundException("Ruta no encontrada con ID: " + id));
        return ResponseEntity.ok(route);
    }

    @Operation(summary = "Obtiene las rutas por nombre que contengan el texto de la búsqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de rutas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Route.class))))})
    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List <Route>> getRoutesByName(@RequestParam("name") String name) {
        List<Route> routes = routeService.findByNameContaining(name);
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    // MÉTODOS POST, PUT Y DELETE

    @Operation(summary = "Añade una nueva ruta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta añadida exitosamente",
                    content = @Content(schema = @Schema(implementation = Route.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, la ruta no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Route> addRoute(@RequestBody Route route) {
        Route addedRoute = routeService.addRoute(route);
        return new ResponseEntity<>(addedRoute, HttpStatus.OK);
    }

    @Operation(summary = "Modifica una ruta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta modificada exitosamente",
                    content = @Content(schema = @Schema(implementation = Route.class))),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Route> modifyRoute(@PathVariable long id, @RequestBody Route route) {
        Route updatedRoute = routeService.modifyRoute(id, route);
        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una ruta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta eliminada exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Response> deleteRoute(@PathVariable long id) {
        routeService.deleteRoute(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(RouteNotFoundException rnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND, rnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

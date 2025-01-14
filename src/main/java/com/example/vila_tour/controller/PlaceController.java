package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Place;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.PlaceNotFoundException;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.service.PlaceService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

/**
 * Controlador para Lugares
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/places")
@RestController
@Tag(name = "Places", description = "Catalogo de lugares")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Operation(summary = "Obtiene el listado de todos lugares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lugares",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class))))})
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Place>> getPlaces() {
        Set<Place> places = placeService.findAllPlaces();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el lugar determinado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logar encontrado",
                    content = @Content(schema = @Schema(implementation = Place.class))),
            @ApiResponse(responseCode = "404", description = "El lugar no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{idPlace}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Place> getPlace(@PathVariable("idPlace") long idPlace) {
        Place place = placeService.findPlaceById(idPlace)
                .orElseThrow(() -> new PlaceNotFoundException(idPlace));
        return new ResponseEntity<>(place, HttpStatus.OK);
    }


    @Operation(summary = "Obtiene todos los lugares ordenados por nombre de forma ascendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares ordenados por nombre en orden",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class))))})
    @GetMapping(value = "/sorted", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Place> getPlacesSortedByName() {
        return placeService.findAllByOrderByName();
    }

    @Operation(summary = "Obtiene todos los lugares ordenados por nombre de forma descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares ordenados por nombre en orden inverso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class))))})
    @GetMapping(value = "/sortedInverse", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Place> getPlacesSortedByNameReversed() {
        return placeService.findAllByOrderByNameDesc();
    }

    @Operation(summary = "Busca lugares por nombre y puntuación promedio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class))))})
    @GetMapping(value = "/search", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Place> searchPlacesByNameAndScore(@RequestParam String name, @RequestParam double score) {
        return placeService.findByNameAndAverageScore(name, score);
    }

    @Operation(summary = "Busca Lugares que contienen una determinada cadena en su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares encontradao",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search/name", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Place> searchPlacesByNameContaining(@RequestParam String name) {
        return placeService.findByNameContaining(name);
    }

    @Operation(summary = "Busca lugares por descripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class))))})
    @GetMapping(value = "/search/description", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Place>> findPlacesByDescription(@RequestParam String description) {
        Set<Place> places = placeService.findPlacesByDescription(description);
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    @Operation(summary = "Busca lugares por puntuación promedio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Place.class))))})
    @GetMapping(value = "/search/score", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Place>> findPlacesByAverageScore(@RequestParam double averageScore) {
        Set<Place> places = placeService.findPlacesByAverageScore(averageScore);
        return new ResponseEntity<>(places, HttpStatus.OK);
    }

    @Operation(summary = "Agrega un nuevo Lugar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lugar agregado",
                    content = @Content(schema = @Schema(implementation = Place.class)))})
    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Place> addPlace(@RequestBody Place place) {
        place.setCreationDate(LocalDateTime.now());
        place.setLastModificationDate(LocalDateTime.now());

        if (place.getImages() != null) place.getImages().forEach(image -> image.setArticle(place));

        Place addedPlace = placeService.addPlace(place);
        return new ResponseEntity<>(addedPlace, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un lugar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar modificado",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "El lugar no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @PutMapping(value = "/{idPlace}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Place> modifyPlace(@PathVariable("idPlace") long idPlace, @RequestBody Place newPlace) {
        newPlace.setLastModificationDate(LocalDateTime.now());

        Place place = placeService.modifyPlace(idPlace, newPlace);
        return new ResponseEntity<>(place, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un lugar por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar eliminado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El lugar no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @DeleteMapping(value = "/{idPlace}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Response> deletePlace(@PathVariable("idPlace") long idPlace) {
        placeService.deletePlace(idPlace);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PlaceNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

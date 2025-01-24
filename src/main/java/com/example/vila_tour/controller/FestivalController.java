package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.exception.IngredientAlreadyExistsException;
import com.example.vila_tour.service.FestivalService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Controlador para Festivales
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/festivals")
@RestController
@Tag(name = "Festivals", description = "Catálogo de festivales y tradiciones")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;


    @Operation(summary = "Obtiene el listado de festivales")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de festivales",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = Festival.class))))})
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Set<Festival>> getFestivals() {
        Set<Festival> festivals = festivalService.findAllFestivals();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el festival determinado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el festival",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El festival no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Festival> getFestival(@PathVariable long id){
        Festival festival = festivalService.findFestivalById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));

        return new ResponseEntity<>(festival, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el festival determinado por su descripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el festival",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El festival no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/search/description", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> findFestivalsByDescription(@RequestParam String description) {
        Set<Festival> festivals = festivalService.findFestivalsByDescription(description);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el festival determinado por su puntuación media")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el festival",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El festival no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/search/score", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> findFestivalsByAverageScore(@RequestParam double averageScore) {
        Set<Festival> festivals = festivalService.findFestivalsByAverageScore(averageScore);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el festival determinado por fecha de inicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el festival",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El festival no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/search/startDate", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> findFestivalsByStartDate(@RequestParam String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
        Set<Festival> festivals = festivalService.findFestivalsByStartDate(parsedStartDate);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de festivales en base a su nombre de forma ascendente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de festivales",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = Festival.class))))})
    @GetMapping(value = "/sorted", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> getFestivalsSortedByName() {
        Set<Festival> festivals;
        festivals = festivalService.findAllByOrderByName();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de festivales en base a su nombre de forma descendente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de festivales",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = Festival.class))))})
    @GetMapping(value = "/sortedInverse", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> getFestivalsSortedByNameInverse() {
        Set<Festival> festivals;
        festivals = festivalService.findAllByOrderByNameDesc();
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el festival determinado por nombre y puntuación media")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el festival",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El festival no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/search/name_score", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> getFestivalsByNameAndAverageScore(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "averageScore", defaultValue = "-1") double averageScore) {

        Set<Festival> festivals;
        festivals = festivalService.findByNameAndAverageScore(name, averageScore);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene los festivales que contengan ese nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el festival",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El festival no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/search/name", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Festival>> getFestivalsByNameContaining(
            @RequestParam(value = "name", defaultValue = "") String name) {

        Set<Festival> festivals;
        festivals = festivalService.findByNameContaining(name);
        return new ResponseEntity<>(festivals, HttpStatus.OK);
    }

    @Operation(summary = "Añade un nuevo festival")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Festival añadido exitosamente",
                    content = @Content(schema = @Schema(implementation = Festival.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, el festival no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Festival> addFestival(@RequestBody Festival festival){
        festival.setCreationDate(LocalDateTime.now());
        festival.setLastModificationDate(LocalDateTime.now());

        if (festival.getImages() != null) festival.getImages().forEach(image -> image.setArticle(festival));

        Festival addedFestival = festivalService.addFestival(festival);
        return new ResponseEntity<>(addedFestival, HttpStatus.OK);
    }

    @Operation(summary = "Añade nuevas categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categorías añadidas exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, las categorías no pudieron añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/bulk", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Response> addRecipes(@RequestBody List<Festival> festivals) {
        try {
            List<Festival> addedFestivals = new ArrayList<>();

            for (Festival festival : festivals) {
                try {
                    // Llama al servicio para agregar cada categoría
                    Festival addedFestival = festivalService.addFestival(festival);
                    addedFestivals.add(addedFestival);
                } catch (IngredientAlreadyExistsException ex) {
                    // Si una categoría ya existe, registra el error, pero no detengas el proceso
                    // Puedes optar por manejar esto de otra manera, por ejemplo, agregando un registro a un log
                }
            }

            // Respuesta exitosa con la lista de categorías añadidas
            return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.CREATED);
        } catch (Exception ex) {
            // Respuesta en caso de error general
            Response errorResponse = Response.errorResponse(500, "Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modifica un festival existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Festival modificado exitosamente",
                    content = @Content(schema = @Schema(implementation = Festival.class))),
            @ApiResponse(responseCode = "404", description = "Festival no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Festival> modifyFestival(@PathVariable long id, @RequestBody Festival newFestival){
        newFestival.setLastModificationDate(LocalDateTime.now());

        // Obtener el festival actual y sus imágenes
        Festival existingFestival = festivalService.findFestivalById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));

        // Mantener las imágenes del festival existente
        if (existingFestival.getImages() != null && !existingFestival.getImages().isEmpty()) {
            newFestival.getImages().addAll(existingFestival.getImages()); // Se agregan las imágenes existentes
        }

        // Si el nuevo festival tiene imágenes, se asignan
        if (newFestival.getImages() != null) {
            newFestival.getImages().forEach(image -> image.setArticle(newFestival));
        }

        Festival festival = festivalService.modifyFestival(id, newFestival);
        return new ResponseEntity<>(newFestival,HttpStatus.OK);
    }

    @Operation(summary = "Elimina un festival por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Festival eliminado exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Festival no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Response> deleteFestival(@PathVariable long id){
        festivalService.deleteFestival(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(FestivalNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(FestivalNotFoundException fnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

package com.example.vila_tour.controller;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.exception.IngredientNotFoundException;
import com.example.vila_tour.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

/**
 * Controlador para Ingredientes
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/ingredients")
@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Operation(summary = "Obtiene el listado de ingredientes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de ingredientes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))))
    })
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Set<Ingredient>> getIngredients() {
        Set<Ingredient> ingredients = ingredientService.findAll();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un ingrediente determinado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente encontrado",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") Long idIngredient) {
        Ingredient ingredient = ingredientService.findIngredientById(idIngredient)
                .orElseThrow(() -> new IngredientNotFoundException(idIngredient));
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene ingredientes por su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))))
    })
    @GetMapping(value = "/name", produces = "application/json")
    public ResponseEntity<Set<Ingredient>> getIngredientsByName(@RequestParam("name") String nameIngredient) {
        Set<Ingredient> ingredients = ingredientService.findIngredientsByNameLike(nameIngredient);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene ingredientes por su categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredientes encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))))
    })
    @GetMapping(value = "/category", produces = "application/json")
    public ResponseEntity<Set<Ingredient>> getIngredientsByCategory(@RequestParam("category") String category) {
        CategoryIngredient categoryIngredient = CategoryIngredient.valueOf(category.toUpperCase());
        Set<Ingredient> ingredients = ingredientService.findIngredientsByCategory(categoryIngredient);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @Operation(summary = "Añade un nuevo ingrediente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente añadido exitosamente",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, el ingrediente no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient addedIngredient = ingredientService.addIngredient(ingredient);
        return new ResponseEntity<>(addedIngredient, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un ingrediente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente modificado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Ingredient> modifyIngredient(@PathVariable("id") Long idIngredient, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.modifyIngredient(idIngredient, newIngredient);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un ingrediente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente eliminado exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteIngredient(@PathVariable("id") Long idIngredient) {
        ingredientService.deleteIngredient(idIngredient);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(IngredientNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

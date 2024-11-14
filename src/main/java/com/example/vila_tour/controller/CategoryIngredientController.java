package com.example.vila_tour.controller;


import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.exception.CategoryIngredientNotFoundException;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.service.CategoryIngredientService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controlador para la categoria de ingredientes
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/categories")
@RestController
@Tag(name = "CategoryIngredient", description = "Categorias asociadas a ingredientes")
public class CategoryIngredientController {

    @Autowired
    private CategoryIngredientService categoryIngredientService;

    @Operation(summary = "Obtiene el listado de categorias de ingredientes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de categorias",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = CategoryIngredient.class))))})
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Set<CategoryIngredient>> getCategoriesIngredients() {
        Set<CategoryIngredient> categoryIngredients = categoryIngredientService.findAll();
        return new ResponseEntity<>(categoryIngredients, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la categoria determinada por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la categoria",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CategoryIngredient> getCategory(@PathVariable long id){
        CategoryIngredient categoryIngredient = categoryIngredientService.findById(id)
                .orElseThrow(() -> new CategoryIngredientNotFoundException(id));

        return new ResponseEntity<>(categoryIngredient, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la categoria que contenga ese nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la categoria",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "La categoria no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/search/name", produces = "application/json")
    public ResponseEntity<Set<CategoryIngredient>> getCategoryByNameContaining(
            @RequestParam(value = "name", defaultValue = "") String name) {

        Set<CategoryIngredient> categoryIngredient;
        categoryIngredient = categoryIngredientService.findByNameContaining(name);
        return new ResponseEntity<>(categoryIngredient, HttpStatus.OK);
    }

    @ExceptionHandler(CategoryIngredientNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(CategoryIngredientNotFoundException fnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

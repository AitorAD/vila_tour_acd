package com.example.vila_tour.controller;


import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.exception.CategoryIngredientNotFoundException;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.exception.IngredientAlreadyExistsException;
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

    @Operation(summary = "Añade una nueva caegoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria añadida exitosamente",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, la categoria no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Response> addCategoryIngredient(@RequestBody CategoryIngredient categoryIngredient) {
        try {
            CategoryIngredient addCategoryIngredient = categoryIngredientService.addCategoryIngredient(categoryIngredient);
            // Successful response, no error
            return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.CREATED);
        } catch (IngredientAlreadyExistsException ex) {
            // Return a 400 Bad Request response with custom error code and message
            Response errorResponse = Response.errorResponse(101, "La categoria ya existe.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // General error response for unexpected exceptions
            Response errorResponse = Response.errorResponse(500, "Internal Server Error: " + ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Modifica una categoria de ingrediente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente modificado exitosamente",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CategoryIngredient> modifyCategory(@PathVariable("id") Long idCategory, @RequestBody CategoryIngredient newCategoryIngredient) {
        CategoryIngredient categoryIngredient = categoryIngredientService.modifyCategoryIngredient(idCategory, newCategoryIngredient);
        if (categoryIngredient != null) {
            return new ResponseEntity<>(categoryIngredient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si no se encuentra el ingrediente
        }
    }

    @Operation(summary = "Elimina un ingrediente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente eliminado exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteIngredient(@PathVariable("id") Long idCategory) {
        categoryIngredientService.deleteCategoryIngredient(idCategory);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
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

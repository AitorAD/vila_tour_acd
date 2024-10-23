package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.service.RecipeService;
import com.example.vila_tour.service.IngredientService; // Asegúrate de importar el servicio
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

import java.util.HashSet;
import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

/**
 * Controlador para Recetas
 * @author TeamAjo
 * @version Curso 2024-2025
 */
@RequestMapping("/recipes")
@RestController
@Tag(name = "Recipes", description = "Catálogo de recetas")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService; // Inyección del servicio de ingredientes

    @Operation(summary = "Obtiene el listado de todas las recetas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de recetas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("")
    public ResponseEntity<Set<Recipe>> getRecipes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        Set<Recipe> recipes = recipeService.findAllRecipes(); // Filtrado opcional por nombre y descripción
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la receta determinada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta encontrada",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping("/{idRecipe}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("idRecipe") long idRecipe) {
        Recipe recipe = recipeService.findRecipeById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException(idRecipe));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las recetas ordenadas por nombre de forma ascendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas ordenadas por nombre",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/sorted")
    public Set<Recipe> getRecipesSortedByName() {
        return recipeService.findAllByOrderByName();
    }

    @Operation(summary = "Obtiene todas las recetas ordenadas por nombre de forma descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas ordenadas por nombre en orden inverso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/sortedInverse")
    public Set<Recipe> getRecipesSortedByNameReversed() {
        return recipeService.findAllByOrderByNameDesc();
    }

    @Operation(summary = "Busca recetas por nombre y puntuación promedio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/search")
    public Set<Recipe> searchRecipesByNameAndScore(@RequestParam String name, @RequestParam double score) {
        return recipeService.findByNameAndAverageScore(name, score);
    }

    @Operation(summary = "Busca recetas que contienen una determinada cadena en su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/search/name")
    public Set<Recipe> searchRecipesByNameContaining(@RequestParam String name) {
        return recipeService.findByNameContaining(name);
    }

    @Operation(summary = "Busca recetas por un conjunto de ingredientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/search/ingredients") // Buscar recetas por ingredientes
    public ResponseEntity<Set<Recipe>> searchRecipesByIngredients(@RequestParam Set<Long> ingredientIds) {
        Set<Ingredient> ingredients = new HashSet<>();
        for (Long id : ingredientIds) {
            Ingredient ingredient = ingredientService.findIngredientById(id).orElse(null);
            if (ingredient != null) {
                ingredients.add(ingredient);
            }
        }

        Set<Recipe> recipes = recipeService.findRecipesByIngredients(ingredients);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Busca recetas por descripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/search/description")
    public ResponseEntity<Set<Recipe>> findRecipesByDescription(@RequestParam String description) {
        Set<Recipe> recipes = recipeService.findRecipesByDescription(description);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Busca recetas por puntuación promedio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/search/score")
    public ResponseEntity<Set<Recipe>> findRecipesByAverageScore(@RequestParam double averageScore) {
        Set<Recipe> recipes = recipeService.findRecipesByAverageScore(averageScore);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Busca recetas que contienen un ingrediente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping("/search/ingredient")
    public ResponseEntity<Set<Recipe>> findRecipesByIngredient(@RequestParam long ingredientId) {
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId)
                .orElseThrow(() -> new RecipeNotFoundException(ingredientId));
        Set<Recipe> recipes = recipeService.findRecipesByIngredient(ingredient);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Agrega una nueva receta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receta agregada",
                    content = @Content(schema = @Schema(implementation = Recipe.class)))})
    @PostMapping("")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica una receta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta modificada",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @PutMapping("/{idRecipe}")
    public ResponseEntity<Recipe> modifyRecipe(@PathVariable("idRecipe") long idRecipe, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.modifyRecipe(idRecipe, newRecipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una receta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta eliminada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @DeleteMapping(value = "/{idRecipe}")
    public ResponseEntity<Response> deleteRecipe(@PathVariable("idRecipe") long idRecipe) {
        recipeService.deleteRecipe(idRecipe);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(RecipeNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

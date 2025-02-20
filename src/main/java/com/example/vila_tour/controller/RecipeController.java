package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.Image;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.exception.IngredientAlreadyExistsException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private IngredientService ingredientService;

    @Operation(summary = "Obtiene el listado de todas las recetas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de recetas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Recipe>> getRecipes() {
        Set<Recipe> recipes = recipeService.findAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la receta determinada por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta encontrada",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{idRecipe}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("idRecipe") long idRecipe) {
        Recipe recipe = recipeService.findRecipeById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException(idRecipe));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las recetas receintes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas recientes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/recent", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Recipe> getAllByRecent(@RequestParam boolean recent) {
        return recipeService.findAllByRecent(recent);
    }

    @Operation(summary = "Obtiene todas las recetas ordenadas por nombre de forma ascendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas ordenadas por nombre",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/sorted", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Recipe> getRecipesSortedByName() {
        return recipeService.findAllByOrderByName();
    }

    @Operation(summary = "Obtiene todas las recetas ordenadas por nombre de forma descendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas ordenadas por nombre en orden inverso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/sortedInverse", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Recipe> getRecipesSortedByNameReversed() {
        return recipeService.findAllByOrderByNameDesc();
    }

    @Operation(summary = "Busca recetas por nombre y puntuación promedio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Recipe> searchRecipesByNameAndScore(@RequestParam String name, @RequestParam double score) {
        return recipeService.findByNameAndAverageScore(name, score);
    }

    @Operation(summary = "Busca recetas que contienen una determinada cadena en su nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search/name", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public Set<Recipe> searchRecipesByNameContaining(@RequestParam String name) {
        return recipeService.findByNameContaining(name);
    }

    @Operation(summary = "Busca recetas por descripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search/description", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Recipe>> findRecipesByDescription(@RequestParam String description) {
        Set<Recipe> recipes = recipeService.findRecipesByDescription(description);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Busca recetas por puntuación promedio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search/score", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Recipe>> findRecipesByAverageScore(@RequestParam double averageScore) {
        Set<Recipe> recipes = recipeService.findRecipesByAverageScore(averageScore);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Busca las recetas que ha creado un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search/creatorId", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Recipe>> findRecipesByCreatorId(@RequestParam long creatorId) {
        Set<Recipe> recipes = recipeService.findRecipesByCreatorId(creatorId);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @Operation(summary = "Busca recetas que contienen un ingrediente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class))))})
    @GetMapping(value = "/search/ingredient", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
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
    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        recipe.setCreationDate(LocalDateTime.now());
        recipe.setLastModificationDate(LocalDateTime.now());

        if (recipe.getImages() != null) recipe.getImages().forEach(image -> image.setArticle(recipe));

        Recipe addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
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
    public ResponseEntity<Response> addRecipes(@RequestBody List<Recipe> recipes) {
        try {
            List<Recipe> addedRecipes = new ArrayList<>();

            for (Recipe recipe : recipes) {
                try {
                    // Llama al servicio para agregar cada categoría
                    Recipe addedRecipe = recipeService.addRecipe(recipe);
                    addedRecipes.add(addedRecipe);
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

    @Operation(summary = "Modifica una receta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta modificada",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @PutMapping(value = "/{idRecipe}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Recipe> modifyRecipe(@PathVariable("idRecipe") long idRecipe, @RequestBody Recipe newRecipe) {
        newRecipe.setLastModificationDate(LocalDateTime.now());

        // Obtener la receta actual y sus imágenes
        Recipe existingRecipe = recipeService.findRecipeById(idRecipe)
                .orElseThrow(() -> new FestivalNotFoundException(idRecipe));

        // Mantener las imágenes de la receta existente
        if (existingRecipe.getImages() != null && !existingRecipe.getImages().isEmpty()) {
            newRecipe.getImages().addAll(existingRecipe.getImages()); // Se agregan las imágenes existentes
        }

        // Si la nueva receta tiene imágenes, se asignan
        if (newRecipe.getImages() != null) {
            newRecipe.getImages().forEach(image -> image.setArticle(newRecipe));
        }

        Recipe recipe = recipeService.modifyRecipe(idRecipe, newRecipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una receta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta eliminada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La receta no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @DeleteMapping(value = "/{idRecipe}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
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

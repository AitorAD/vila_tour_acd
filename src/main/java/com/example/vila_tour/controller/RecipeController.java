package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.service.RecipeService;
import com.example.vila_tour.service.IngredientService; // Asegúrate de importar el servicio
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

@RequestMapping("/recipes")
@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService; // Inyección del servicio de ingredientes

    @Autowired
    private EntityManagerFactory emf;

    @GetMapping("")
    public ResponseEntity<Set<Recipe>> getRecipes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {
        Set<Recipe> recipes = recipeService.findAllRecipes(); // Filtrado opcional por nombre y descripción
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{idRecipe}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("idRecipe") long idRecipe) {
        Recipe recipe = recipeService.findRecipeById(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException(idRecipe));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public Set<Recipe> getRecipesSortedByName() {
        return recipeService.findAllByOrderByName();
    }

    @GetMapping("/sortedInverse")
    public Set<Recipe> getRecipesSortedByNameReversed() {
        return recipeService.findAllByOrderByNameDesc();
    }

    @GetMapping("/search")
    public Set<Recipe> searchRecipesByNameAndScore(@RequestParam String name, @RequestParam double score) {
        return recipeService.findByNameAndAverageScore(name, score);
    }

    @GetMapping("/search/name")
    public Set<Recipe> searchRecipesByNameContaining(@RequestParam String name) {
        return recipeService.findByNameContaining(name);
    }

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


    // Buscar recetas por descripción
    @GetMapping("/search/description")
    public ResponseEntity<Set<Recipe>> findRecipesByDescription(@RequestParam String description) {
        Set<Recipe> recipes = recipeService.findRecipesByDescription(description);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    // Buscar recetas por puntuación promedio
    @GetMapping("/search/score")
    public ResponseEntity<Set<Recipe>> findRecipesByAverageScore(@RequestParam double averageScore) {
        Set<Recipe> recipes = recipeService.findRecipesByAverageScore(averageScore);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    // Buscar recetas por ingrediente
    @GetMapping("/search/ingredient")
    public ResponseEntity<Set<Recipe>> findRecipesByIngredient(@RequestParam long ingredientId) {
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId)
                .orElseThrow(() -> new RecipeNotFoundException(ingredientId));
        Set<Recipe> recipes = recipeService.findRecipesByIngredient(ingredient);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
    }

    @PutMapping("/{idRecipe}")
    public ResponseEntity<Recipe> modifyRecipe(@PathVariable("idRecipe") long idRecipe, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.modifyRecipe(idRecipe, newRecipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

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

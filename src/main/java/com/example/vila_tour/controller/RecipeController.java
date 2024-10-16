package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.service.RecipeService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

@RequestMapping("/recipes")
@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private EntityManagerFactory emf;

    @GetMapping("")
    public ResponseEntity<Set<Recipe>> getRecipes() {
        // TODO introducir RequestParams
        Set<Recipe> recipes = new HashSet<>();
        recipes = recipeService.findAll();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{idRecipe}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("idRecipe") int idRecipe) {
        Recipe recipe = recipeService.findByIdArticle(idRecipe)
                .orElseThrow(() -> new RecipeNotFoundException(idRecipe));

        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.OK);
    }

    @PutMapping("/{idRecipe}")
    public ResponseEntity<Recipe> modifyRecipe(@PathVariable("idRecipe") int idRecipe, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.modifyRecipe(idRecipe, newRecipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{idRecipe}")
    public ResponseEntity<Response> deleteRecipe(@PathVariable("idRecipe") int idRecipe) {
        recipeService.deleteRecipe(idRecipe);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(RecipeNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

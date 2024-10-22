package com.example.vila_tour.controller;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.exception.IngredientNotFoundException;
import com.example.vila_tour.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

@RequestMapping("/ingredients")
@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("")
    public ResponseEntity<Set<Ingredient>> getIngredients() {
        Set<Ingredient> ingredients = ingredientService.findAll();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") Long idIngredient) {
        Ingredient ingredient = ingredientService.findIngredientById(idIngredient)
                .orElseThrow(() -> new IngredientNotFoundException(idIngredient));
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Set<Ingredient>> getIngredientsByName(@RequestParam("name") String nameIngredient) {
        Set<Ingredient> ingredients = ingredientService.findIngredientsByNameLike(nameIngredient);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<Set<Ingredient>> getIngredientsByCategory(@RequestParam("category") String category) {
        CategoryIngredient categoryIngredient = CategoryIngredient.valueOf(category.toUpperCase());
        Set<Ingredient> ingredients = ingredientService.findIngredientsByCategory(categoryIngredient);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient addedIngredient = ingredientService.addIngredient(ingredient);
        return new ResponseEntity<>(addedIngredient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> modifyIngredient(@PathVariable("id") Long idIngredient, @RequestBody Ingredient newIngredient) {
        Ingredient ingredient = ingredientService.modifyIngredient(idIngredient, newIngredient);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

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

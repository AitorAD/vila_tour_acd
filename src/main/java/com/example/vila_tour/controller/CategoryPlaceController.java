package com.example.vila_tour.controller;

import com.example.vila_tour.domain.*;
import com.example.vila_tour.exception.CategoryPlaceAlreadyExist;
import com.example.vila_tour.exception.CategoryPlaceNotFoundException;
import com.example.vila_tour.service.CategoryPlaceService;
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

import java.util.Set;

/**
 * Controlador para la categoria de lugares
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/categoriesPlace")
@RestController
@Tag(name = "CategoryPlace", description = "Categorias asociadas a los lugares")
public class CategoryPlaceController {

    @Autowired
    private CategoryPlaceService categoryPlaceService;

    @Operation(summary = "Obtiene el listado de categorias de ingredientes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryPlace.class))))})
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<CategoryPlace>> getCategoryPlaces(){
        Set<CategoryPlace> categoryPlaces = categoryPlaceService.findAll();
        return new ResponseEntity<>(categoryPlaces, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la categorias determinad por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la categoria",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "La categoría no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{idPlace}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<CategoryPlace> getCategoryPlace(@PathVariable("idPlace") long id){
        CategoryPlace categoryPlace = categoryPlaceService.findById(id)
                .orElseThrow(() -> new CategoryPlaceNotFoundException(id));

        return new ResponseEntity<>(categoryPlace, HttpStatus.OK);
    }

    @Operation(summary = "Añade una nueva caegoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria añadida exitosamente",
                    content = @Content(schema = @Schema(implementation = Place.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida, la categoria no pudo añadirse",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Response> addCategoryPlace(@RequestBody CategoryPlace categoryPlace) {
        try {
            CategoryPlace addCategoryPlace = categoryPlaceService.addCategoryPlace(categoryPlace);
            return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.CREATED);
        } catch (CategoryPlaceAlreadyExist ex){
            Response errorResponse = Response.errorResponse(101, "La categoria ya existe");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            Response errorResponse = Response.errorResponse(500, "Internal Server Error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Modifica una categoria de lugar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria modificada exitosamente",
                    content = @Content(schema = @Schema(implementation = Place.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud no válida",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<CategoryPlace> modifyCategory(@PathVariable("id") Long idCategory, @RequestBody CategoryPlace newCategoryPlace) {
        CategoryPlace categoryPlace = categoryPlaceService.modifyCategoryPlace(idCategory, newCategoryPlace);
        if (categoryPlace != null) {
            return new ResponseEntity<>(categoryPlace, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Elimina una categoria por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria eliminada exitosamente",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrado",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Response> deleteCategoryIngredient(@PathVariable("id") Long idCategory) {
        categoryPlaceService.deleteCategoryPlace(idCategory);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(CategoryPlaceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(CategoryPlaceNotFoundException fnfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

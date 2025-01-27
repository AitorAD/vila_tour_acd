package com.example.vila_tour.controller;


import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Image;
import com.example.vila_tour.exception.ArticleNotFoundException;
import com.example.vila_tour.exception.ImageNotFoundException;
import com.example.vila_tour.service.ArticleService;
import com.example.vila_tour.service.ImageService;
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

import java.util.Optional;
import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

/**
 * Controlador para Imagenes
 * @author Team AJO
 * @version curso 2024-2025
 */
@RequestMapping("/images")
@RestController
@Tag(name = "Images", description = "Controlador de imagenes")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Obtiene el listado de imagenes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de imagenes",
                    content = @Content(array = @ArraySchema(schema =  @Schema(implementation = Image.class))))})
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Image>> getImages() {
        Set<Image> images = imageService.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la imagen determinada por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe la imagen",
                    content = @Content(schema =  @Schema(implementation = Image.class))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{idImage}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Image> getImage(@PathVariable("idImage") long idImage){
        Image image = imageService.findById(idImage)
                .orElseThrow(() -> new ImageNotFoundException(idImage));

        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Operation(summary = "Busca imagenes que contienen un articulo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagenes encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Image.class))))})
    @GetMapping(value = "/getImagesByArticle/{idArticle}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Set<Image>> findImagesByArticle(@PathVariable long idArticle) {
        Article article = articleService.findArticleById(idArticle)
                .orElseThrow(() -> new ArticleNotFoundException(idArticle));
        Set<Image> images = imageService.findImagesByArticle(article);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @Operation(summary = "Busca la primera imagen que contiene un articulo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen encontradas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Image.class))))})
    @GetMapping(value = "/getImageByArticle/{idArticle}", produces = "application/json")
    public ResponseEntity<Image> findImageByArticle(@PathVariable long idArticle) {
        Article article = articleService.findArticleById(idArticle)
                .orElseThrow(() -> new ArticleNotFoundException(idArticle));

        Image image = imageService.findImageByArticle(article);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Operation(summary = "Agrega una nueva imagen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen agregada",
                    content = @Content(schema = @Schema(implementation = Image.class)))})
    @PostMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        Image addedImage = imageService.addImage(image);
        return new ResponseEntity<>(addedImage, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica una imagen existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen modificada",
                    content = @Content(schema = @Schema(implementation = Image.class))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @PutMapping(value = "/{idImage}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Image> modifyRecipe(@PathVariable("idImage") long idImage, @RequestBody Image newImage) {
        Image image = imageService.modifyImage(idImage, newImage);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una imagen por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen eliminada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Response> deleteImage(@PathVariable("id") long idImage) {
        imageService.deleteImage(idImage);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Elimina todas las imagenes pertenecientes a un articulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen eliminada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "La imagen no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @DeleteMapping(value = "/deleteAllByArticle/{id}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Response> deleteAllByArticle(@PathVariable("id") long idArticle) {
        Optional<Article> article = articleService.findArticleById(idArticle);

        // Validar si el artículo existe
        if (article.isEmpty()) {
            throw new IllegalArgumentException("El artículo con ID " + idArticle + " no existe");
        }

        // Eliminar todas las imágenes asociadas
        imageService.deleteAllByArticle(article.get());

        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ImageNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

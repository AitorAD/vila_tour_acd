package com.example.vila_tour.controller;


import com.example.vila_tour.domain.Article;
import com.example.vila_tour.exception.ArticleNotFoundException;
import com.example.vila_tour.service.ArticleService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Controlador para Articulos
 * @author TeamAjo
 * @version Curso 2024-2025
 */
@RequestMapping("/articles")
@RestController
@Tag(name = "Articles", description = "Cotrolador de articulos")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Obtiene el listado de todos los articulos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de articulos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Article.class))))})
    @GetMapping(value = "", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR')")
    public ResponseEntity<Set<Article>> getArticles() {
        Set<Article> articles = articleService.findAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el articulo determinado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articulo encontrado",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El articulo no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{idRecipe}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('EDITOR') or hasAuthority('USER')")
    public ResponseEntity<Article> getArticle(@PathVariable("idArticle") long idArticle) {
        Article article = articleService.findArticleById(idArticle)
                .orElseThrow(() -> new ArticleNotFoundException(idArticle));
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
}

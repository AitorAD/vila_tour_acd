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
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

/**
 * Controlador para Articulos
 * @author Team AJO
 * @version curso 2024-20258
 */
@RequestMapping("/articles")
@RestController
@Tag(name = "Articles", description = "Cátalogo de artículos")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Obtiene el listado de artículos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listadp de articulos",
            content = @Content(array = @ArraySchema(schema =  @Schema(implementation = Article.class))))})
    @GetMapping(value = "", produces = "articles/json")
    public ResponseEntity<Set<Article>> getArticles(){
        Set<Article> articles = null;
        articles = articleService.findAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el artículos determinado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el articulo",
                    content = @Content(schema =  @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))})
    @GetMapping(value = "/{id}", produces = "article/json")
    public ResponseEntity<Article> getArticle(@PathVariable("id") Long idArticle) {
        Article article = articleService.findArticleById(idArticle)
                .orElseThrow(() -> new ArticleNotFoundException(idArticle));
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @Operation(summary = "Elimina el producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el producto",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/{id}", produces = "article/json")
    public ResponseEntity<Response> deleteArticle(@PathVariable("id") Long idArticle){
        articleService.findArticleById(idArticle).orElseThrow(()-> new ArticleNotFoundException(idArticle));
        articleService.deleteArticle(idArticle);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(ArticleNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

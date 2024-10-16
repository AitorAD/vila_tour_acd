package com.example.vila_tour.controller;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.exception.ArticleNotFoundException;
import com.example.vila_tour.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.vila_tour.controller.Response.NOT_FOUND;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<Set<Article>> getArticles(){
        Set<Article> articles = null;
        articles = articleService.findAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable("id") Long idArticle) {
        Article article = articleService.findArticleById(idArticle)
                .orElseThrow(() -> new ArticleNotFoundException());
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteArticle(@PathVariable("id") Long idArticle){
        articleService.findArticleById(idArticle);
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

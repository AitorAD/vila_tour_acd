package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;

import java.util.Optional;
import java.util.Set;

public interface ArticleService {
    Set<Article> findAllArticles();
    Optional<Article> findArticleById(long idArticle);
    void recalculateAverageScore(Long articleId);
    void deleteArticle(long idArticle);
}

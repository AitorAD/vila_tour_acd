package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.exception.ArticleNotFoundException;
import com.example.vila_tour.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    public Set<Article> findAllArticles(){
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findArticleById(long idArticle) {
        return articleRepository.findById(idArticle);
    }

    @Override
    public void deleteArticle(long idArticle){
        articleRepository.findById(idArticle).
                orElseThrow(() -> new ArticleNotFoundException(idArticle));
        articleRepository.deleteById(idArticle);
    }

    @Transactional
    public void recalculateAverageScore(Long articleId) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);
        if (articleOpt.isPresent()) {
            Article article = articleOpt.get();
            article.updateAverageScore();
            articleRepository.save(article); // Persiste el cambio en la BD
        }
    }
}

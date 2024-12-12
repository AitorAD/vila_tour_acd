package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.ArticleNotFoundException;
import com.example.vila_tour.exception.ReviewNotFoundException;
import com.example.vila_tour.exception.UserNotFoundException;
import com.example.vila_tour.repository.ArticleRepository;
import com.example.vila_tour.repository.ReviewRepository;
import com.example.vila_tour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleService articleService;

    @Override
    public Set<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(long idUser, long idArticle){
        return  reviewRepository.findById(new ReviewId(idUser, idArticle));
    }

    @Override
    public Set<Review> findByRatingAndArticle(long rating, long idArticle) {
        return reviewRepository.findByRatingAndArticle(rating, idArticle);
    }

    @Override
    public Set<Review> findByRatingAndUser(long rating, long userId) {
        return reviewRepository.findByRatingAndUser(rating, userId);
    }

    @Override
    public Set<Review> findByArticle(long articleId) {
        return reviewRepository.findByArticle(articleId);
    }

    @Override
    public Set<Review> findByUser(long userId) {
        return reviewRepository.findByUser(userId);
    }

    @Override
    public Review addReview(Review review, Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Articulo no encontrado"));
        review.setUser(user);
        review.setArticle(article);
        articleService.recalculateAverageScore(article.getId());
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(ReviewId reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

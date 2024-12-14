package com.example.vila_tour.service;

import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;

import java.util.Optional;
import java.util.Set;

public interface ReviewService {
    Set<Review> findAll();
    Optional<Review> findById(long idUser, long idArticle);
    Set<Review> findByRatingAndArticle(long rating, long articleId);
    Set<Review> findByRatingAndUser(long rating, long userId);
    Set<Review> findByArticle(long articleId);
    Set<Review> findByUser(long userId);
    Review addReview(Review review, Long idUser, Long idArticle);

    void deleteReview(ReviewId id);
}

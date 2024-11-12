package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
import com.example.vila_tour.domain.User;

import java.util.Set;

public interface ReviewService {
    Set<Review> findAll();
    Set<Review> findByRatingAndArticle(long rating, long articleId);
    Set<Review> findByRatingAndUser(long rating, long userId);
    Set<Review> findByArticle(long articleId);
    Set<Review> findByUser(long userId);
    Review addReview(Review review);
    void deleteReview(ReviewId id);
}

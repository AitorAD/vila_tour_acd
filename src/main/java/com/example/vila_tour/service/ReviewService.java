package com.example.vila_tour.service;

import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;

import java.util.Set;

public interface ReviewService {
    Set<Review> findByRatingAndArticle(long rating, long idArticle);
    Set<Review> findByRatingAndUser(long rating, long idUser);
    Set<Review> findByArticle(long idArticle);
    Set<Review> findByUser(long idUser);
    Review addReview(Review review);
    Review modifyReview(ReviewId id, Review newReview);
    void deleteReview(ReviewId id);
}

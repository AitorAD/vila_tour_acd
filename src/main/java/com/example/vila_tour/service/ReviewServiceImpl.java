package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.ReviewNotFoundException;
import com.example.vila_tour.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Set<Review> findAll() {
        return reviewRepository.findAll();
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
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(ReviewId id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.delete(review);
    }
}

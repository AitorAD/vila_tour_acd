package com.example.vila_tour.service;

import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
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
    public Set<Review> findByRatingAndArticle(long rating, long idArticle) {
        return reviewRepository.findByRatingAndArticle(rating, idArticle);
    }

    @Override
    public Set<Review> findByRatingAndUser(long rating, long idUser) {
        return reviewRepository.findByRatingAndUser(rating, idUser);
    }

    @Override
    public Set<Review> findByArticle(long idArticle) {
        return reviewRepository.findByArticle(idArticle);
    }

    @Override
    public Set<Review> findByUser(long idUser) {
        return reviewRepository.findByUser(idUser);
    }

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review modifyReview(ReviewId id, Review newReview) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        newReview.setId(review.getId());
        return reviewRepository.save(newReview);
    }

    @Override
    public void deleteReview(ReviewId id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        reviewRepository.delete(review);
    }
}

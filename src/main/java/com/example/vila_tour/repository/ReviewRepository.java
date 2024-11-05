package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    Set<Review> findByRatingAndArticle(long rating, long idArticle);
    Optional<Review> findById(ReviewId id);
    Set<Review> findByRatingAndUser(long rating, long idUser);
    Set<Review> findByArticle(long idArticle);
    Set<Review> findByUser(long idUser);
}

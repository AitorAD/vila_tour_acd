package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Review;
import com.example.vila_tour.domain.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ReviewRepository extends CrudRepository<Review, ReviewId> {

    Set<Review> findAll();

    Optional<Review> findById(ReviewId id);

    @Query(value = "SELECT * FROM Review WHERE article_id = :article_id AND rating = :rating", nativeQuery = true)
    Set<Review> findByRatingAndArticle(@Param("rating") long rating, @Param("article_id") long articleId);

    @Query(value = "SELECT * FROM Review WHERE user_id = :userId AND rating = :rating", nativeQuery = true)
    Set<Review> findByRatingAndUser(@Param("rating") long rating, @Param("userId") long userId);

    @Query(value = "SELECT * FROM Review WHERE article_id = :articleId", nativeQuery = true)
    Set<Review> findByArticle(@Param("articleId") long articleId);

    @Query(value = "SELECT * FROM Review WHERE user_id = :userId", nativeQuery = true)
    Set<Review> findByUser(@Param("userId") long userId);
}

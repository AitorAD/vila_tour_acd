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

    @Query("SELECT r FROM Review r WHERE r.article.id = :articleId AND r.rating = :rating")
    Set<Review> findByRatingAndArticle(@Param("rating") long rating, @Param("articleId") long articleId);

    Optional<Review> findById(ReviewId id);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId AND r.rating = :rating")
    Set<Review> findByRatingAndUser(@Param("rating") long rating, @Param("userId") long userId);

    @Query("SELECT r FROM Review r WHERE r.article.id = :articleId")
    Set<Review> findByArticle(@Param("articleId") long articleId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    Set<Review> findByUser(@Param("userId") long userId);
}

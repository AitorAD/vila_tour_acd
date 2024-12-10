package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Set<Image> findAll();
    Optional<Image> findById(long id);
    Set<Image> findByArticle(Article article);
}

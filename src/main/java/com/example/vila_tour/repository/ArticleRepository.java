package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Article;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Set<Article> findAll();

    Optional<Article> findById(long id);

}

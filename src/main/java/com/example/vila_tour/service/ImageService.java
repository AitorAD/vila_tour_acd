package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Image;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface ImageService {
    Set<Image> findAll();
    Optional<Image> findById(long id);
    Set<Image> findImagesByArticle(Article article);
    Image findImageByArticle(Article article);
    Image addImage(Image image);
    Image modifyImage(long id, Image newImage);
    void deleteImage(long id);
    void deleteAllByArticle(Article article);
}

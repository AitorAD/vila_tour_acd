package com.example.vila_tour.service;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.Image;
import com.example.vila_tour.exception.ImageNotFoundException;
import com.example.vila_tour.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Set<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Set<Image> findImagesByArticle(Article article) {
        return imageRepository.findImagesByArticle(article);
    }

    @Override
    public Image findImageByArticle(Article article) {
        // return imageRepository.findOneImageByArticle(article);
        return imageRepository.findImagesByArticle(article).stream().toList().get(0);
    }

    @Override
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image modifyImage(long id, Image newImage) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));

        newImage.setId(image.getId());
        return imageRepository.save(newImage);
    }

    @Override
    public void deleteImage(long id) {
        imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
        imageRepository.deleteById(id);
    }

    @Override
    public void deleteAllByArticle(Article article) {
        // TODO: Manejar esto mediante el repository
        Set<Image> imagesToRemove = findImagesByArticle(article);
        imagesToRemove.forEach(image -> deleteImage(image.getId()));
    }
}

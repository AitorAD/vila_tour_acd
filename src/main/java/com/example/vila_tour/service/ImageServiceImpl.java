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
    public Set<Image> findByArticle(Article article) {
        return imageRepository.findByArticle(article);
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
    public void deleteAllByFestival(long id) {
        // TODO
    }
}

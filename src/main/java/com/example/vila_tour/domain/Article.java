package com.example.vila_tour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "article")
public abstract class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idArticle;
    @Column
    private String nameArticle;
    @Column
    private String descriptionArticle;
    @Column
    private double averageScoreArticle;
    @Column
    private Image imageArticle;

    //TODO metodos
}

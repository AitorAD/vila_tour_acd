package com.example.vila_tour.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "article_id"})})
public class Review {

    @EmbeddedId
    private ReviewId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private long rating;
    private String comment;
    private LocalDateTime postDate;
    private boolean favorite;
}

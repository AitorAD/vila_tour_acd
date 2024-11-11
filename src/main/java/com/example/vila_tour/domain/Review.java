package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @MapsId("user_id")
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("article_id")
    @JoinColumn(name = "article_id", nullable = false)
    @JsonIgnore
    private Article article;

    private long rating;
    private String comment;
    private LocalDateTime postDate;
    private boolean favorite;
}

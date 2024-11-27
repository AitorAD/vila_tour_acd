package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "coordinates")
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @OneToOne(mappedBy = "coordinate", cascade = CascadeType.PERSIST)
    private Place place;

    @OneToMany(mappedBy = "coordinate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Festival> festivals;

}

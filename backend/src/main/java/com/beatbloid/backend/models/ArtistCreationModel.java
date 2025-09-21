package com.beatbloid.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "artist_creations")
@Data
public class ArtistCreationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String url;
    private Boolean featured;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistModel artist;
}

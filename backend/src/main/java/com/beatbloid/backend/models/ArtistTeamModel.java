package com.beatbloid.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "artist_teams")
@Data
public class ArtistTeamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistModel artist;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private TeamModel team;
}

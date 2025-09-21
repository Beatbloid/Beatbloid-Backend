package com.beatbloid.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "artists")
@Data
public class ProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    @OneToOne
    @JoinColumn(name = "profile_id",nullable = fale)
    private ProfileModel profile;
}

package com.beatbloid.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class ReviewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientModel client;

    private double rating;
    
    @Column(columnDefinition = "TEXT")
    private String message;
}

package com.beatbloid.backend.models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "event_media")
@Data
public class EventMediaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventModel event;

}

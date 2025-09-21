package com.beatbloid.backend.models;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "events")
@Data
public class EventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    
    private String eventType;
    private Long clientId;
    private String eventVenue;
    private int eventSize;
    private double eventBudget;
    private int eventDuration;
    private Date eventDate;
    private String eventAddress;
    private Boolean featured;
    
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventMediaModel> media;
    
}


package com.beatbloid.backend.controllers;

import com.beatbloid.backend.dto.ResponseWrapper;
import com.beatbloid.backend.models.EventModel;
import com.beatbloid.backend.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<EventModel>> createEvent(
            @RequestPart("event") Long eventId,
            @RequestPart("mediaFile") MultipartFile mediaFile
    ) {
        EventModel createdEvent = eventService.createEvent(eventId, mediaFile);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Event created successfully", createdEvent));
    }

    @PostMapping("/host")
    public ResponseEntity<ResponseWrapper<EventModel>> hostEvent(
            @RequestPart("event") EventModel event
    ) {
        EventModel createdEvent = eventService.hostEvent(event);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Event created successfully", createdEvent));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<EventModel>>> getAllEvents() {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Events fetched successfully", eventService.getAllEvents()));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ResponseWrapper<Optional<EventModel>>> getEventById(@PathVariable Long eventId) {
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Event details fetched successfully", eventService.getEventById(eventId)));
    }
}

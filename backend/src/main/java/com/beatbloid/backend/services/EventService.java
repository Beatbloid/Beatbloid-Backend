package com.beatbloid.backend.services;

import com.beatbloid.backend.models.EventModel;
import com.beatbloid.backend.exceptions.NotFoundException;
import com.beatbloid.backend.models.EventMediaModel;
import com.beatbloid.backend.repositories.EventRepository;
import com.beatbloid.backend.repositories.EventMediaRepository;
import com.beatbloid.backend.utils.GoogleDriveUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMediaRepository eventMediaRepository;
    private final GoogleDriveUtil googleDriveUtil;

    public EventService(EventRepository eventRepository, EventMediaRepository eventMediaRepository, GoogleDriveUtil googleDriveUtil) {
        this.eventRepository = eventRepository;
        this.eventMediaRepository = eventMediaRepository;
        this.googleDriveUtil = googleDriveUtil;
    }

    public EventModel createEvent(Long eventId, MultipartFile mediaFile) {

        EventModel event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("No event found"));
        String fileUrl = googleDriveUtil.uploadFile(mediaFile);
        EventMediaModel eventMedia = new EventMediaModel();
        eventMedia.setEvent(event);
        eventMedia.setFileUrl(fileUrl);
        eventMedia.setFileType(mediaFile.getContentType());
        eventMediaRepository.save(eventMedia);

        return event;
    }

    public EventModel hostEvent(EventModel event) {
        EventModel savedEvent = eventRepository.save(event);
        return savedEvent;
    }


    public List<EventModel> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<EventModel> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }
}

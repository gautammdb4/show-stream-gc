package com.showstream.eventcatalog.service.impl;

import com.showstream.eventcatalog.dto.EventRequestDto;
import com.showstream.eventcatalog.dto.EventResponseDto;
import com.showstream.eventcatalog.entity.Event;
import com.showstream.eventcatalog.repository.EventRepository;
import com.showstream.eventcatalog.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository ;

    @Override
    public EventResponseDto saveEvent(EventRequestDto event) {

        Event eventData = Event.builder()
                .title(event.title())
                .description(event.description())
                .genre(event.genre())
                .category(event.category())
                .language(event.language())
                .durationMinutes(event.durationMinutes())
                .releaseDate(event.releaseDate())
                .isActive(Boolean.TRUE)
                .createdAt(LocalDateTime.now())
                .build();
        Event save = eventRepository.save(eventData);
        return EventResponseDto.builder()
                .id(save.getId())
                .title(save.getTitle())
                .description(save.getDescription())
                .genre(save.getGenre())
                .category(save.getCategory())
                .language(save.getLanguage())
                .durationMinutes(save.getDurationMinutes())
                .releaseDate(save.getReleaseDate())
                .isActive(save.getIsActive())
                .createdAt(save.getCreatedAt())
        .build();

    }
}

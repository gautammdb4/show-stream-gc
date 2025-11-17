package com.showstream.eventcatalog.controller;

import com.showstream.eventcatalog.dto.EventRequestDto;
import com.showstream.eventcatalog.dto.EventResponseDto;
import com.showstream.eventcatalog.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/events")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;


//
//    @GetMapping
//    public List<Event> getActiveEvents() {
//        return eventRepository.findByIsActiveTrue();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
//        return eventRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
    // New POST endpoint to register/create an event
    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto event) {

        EventResponseDto savedEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }
}

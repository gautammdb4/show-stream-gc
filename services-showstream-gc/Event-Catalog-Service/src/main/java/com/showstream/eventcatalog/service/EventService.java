package com.showstream.eventcatalog.service;

import com.showstream.eventcatalog.dto.EventRequestDto;
import com.showstream.eventcatalog.dto.EventResponseDto;

public interface EventService {
    EventResponseDto saveEvent(EventRequestDto event);
}

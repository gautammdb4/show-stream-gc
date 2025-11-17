package com.showstream.eventcatalog.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Builder
public record EventResponseDto (
        UUID id,
        String title,
        String description,
        String category,
        String genre,
        Integer durationMinutes,
        String language,
        LocalDate releaseDate,
        Boolean isActive,
        LocalDateTime createdAt
) {
}

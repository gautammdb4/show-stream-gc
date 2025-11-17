package com.showstream.eventcatalog.dto;

import java.time.LocalDate;

public record EventRequestDto(
        String title,
        String description,
         String category,
         String genre,
         Integer durationMinutes,
         String language,
         LocalDate releaseDate
) {
}

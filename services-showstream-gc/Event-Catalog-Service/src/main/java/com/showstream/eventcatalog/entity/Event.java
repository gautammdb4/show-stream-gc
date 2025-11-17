package com.showstream.eventcatalog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Data
@Builder
public class Event {

    @Id
    @UuidGenerator
    private UUID id;

    private String title;
    private String description;
    private String category;
    private String genre;
    private Integer durationMinutes;
    private String language;
    private LocalDate releaseDate;
    private Boolean isActive ;

    private LocalDateTime createdAt = LocalDateTime.now();
}

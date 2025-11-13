package com.showstream.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard DTO for returning structured error responses to the client.
 */

public record ErrorResponse (

    LocalDateTime timestamp,
    String status,
    int statusCode,
    String error,
    String message,
    String path )
{}




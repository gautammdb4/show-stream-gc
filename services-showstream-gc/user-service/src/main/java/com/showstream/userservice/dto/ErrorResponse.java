package com.showstream.userservice.dto;

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




package com.showstream.userservice.dto;


import lombok.Builder;

@Builder
public record AuthResponse(String jwt) {

}

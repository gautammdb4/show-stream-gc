package com.showstream.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {

    /**
     * Validates the given token by making a POST request to the User Service.
     *
     * @param token the token to be validated
     */
    @PostMapping("/validate-token")
    void validateToken(@RequestParam String token);


}

package com.showstream.apigateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE" , path = "/v1/api/users")
public interface UserServiceClient {

    /**
     * Validates the given token by making a POST request to the User Service.
     */
    @PostMapping("/validate-token")
    void validateToken(@RequestParam String token);


}

package com.showstream.apigateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    /**
     * Defines the PasswordEncoder bean.
     * We use BCryptPasswordEncoder, which is the industry standard
     * for securely hashing passwords in Spring applications.
     * * The @Bean annotation ensures this method's return value is available
     * for injection (autowiring) into other classes like UserServiceImpl.
     * * @return The configured PasswordEncoder instance.
     */

    private JwtFilter jwtFilter ;

    // Define the list of public endpoints
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/v1/api/users/login",
            "/v1/api/users/status",
            "/v1/api/users/register"
    );


    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route("USER-SERVICE" , r->r.path("/v1/api/users/**")
                .filters(f-> f.filters(jwtFilter.apply(new JwtFilter.Config()
                        .setPublicEndpoints(PUBLIC_ENDPOINTS)))).uri("lb://USER-SERVICE")
                )
                .build();
    }
}

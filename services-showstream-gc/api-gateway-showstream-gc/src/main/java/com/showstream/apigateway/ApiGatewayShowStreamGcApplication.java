package com.showstream.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayShowStreamGcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayShowStreamGcApplication.class, args);
	}



}

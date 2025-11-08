package com.example.MatchUp.MatchUp_discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MatchUpDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchUpDiscoveryServiceApplication.class, args);
	}

}

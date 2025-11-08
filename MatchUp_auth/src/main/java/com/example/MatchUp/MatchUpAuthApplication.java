package com.example.MatchUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MatchUpAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchUpAuthApplication.class, args);
	}

}

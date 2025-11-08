package com.example.MatchUp.MatchUp_Event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MatchUpEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchUpEventApplication.class, args);
	}

}

package com.ransankul.priceaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class PriceactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceactionApplication.class, args);
	}

	@Bean
	public ObjectMapper initiliazeObjectMapper(){
		return new ObjectMapper();
	}

	@Bean
	public RestTemplate initiliazeRestTemplate(){
		return new RestTemplate();
	}

}

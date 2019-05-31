package com.brokeshirts.ecom;

import com.brokeshirts.ecom.storage.StorageProperties;
import com.brokeshirts.ecom.storage.StorageService;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@RestController
public class EcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

	@RequestMapping(value="/user")
	public Principal user(Principal principal) {
		return principal;
	}
}

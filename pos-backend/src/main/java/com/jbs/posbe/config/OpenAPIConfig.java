package com.jbs.posbe.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition (
		info=@Info (
				title = "POS Management",
				description = "Point Of Sales Management Application Backend",
				contact = @Contact (
						name = "Jayanta B. Sen",
						email = "jayanta.b.sen@yopmail.com"
				),
				version = "1.0.0"
		),
		servers = {
				@Server (
					description = "DEV",
					url = "http://localhost:8080"
				),
				@Server (
					description = "UAT",
					url = "http://localhost:8081"
				),
				@Server (
					description = "PROD",
					url = "http://localhost:8082"
				)
		}
)

public class OpenAPIConfig {
	
}

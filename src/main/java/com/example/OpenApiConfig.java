package com.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				version ="1.0",
				description = "Zoho people backend clone",
				summary = "Employee apis,Department apis,leave tracker apis,time tracker apis etc",
				title = "Zoho people",
				contact = @Contact(
						email = "gandhipawan054@gmail.com",
						name = "Pawan Gandhi"
						)
				)
//		servers = { @Server(
//				url = "localhost:1800/api/gateway",
//				description = "Test Env"
//				),
//				@Server(
//						description = "Prod env",
//						url = "localhost:1900/api/gateway"
//						)
//		}
		
		)
public class OpenApiConfig {

}

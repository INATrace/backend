package com.abelium.inatrace.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("INATrace Services API")
						.description("INATrace Services API OpenAPI documentation")
						.version("1.0")
						.license(new License().name("AGPL-3.0 license")))
				.schemaRequirement("Access token", new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.in(SecurityScheme.In.COOKIE)
						.name("inatrace-accessToken")
						.bearerFormat("JWT")
						.scheme("Bearer"))
				.specVersion(SpecVersion.V31);
	}

}

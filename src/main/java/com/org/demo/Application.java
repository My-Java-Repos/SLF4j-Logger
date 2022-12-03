package com.org.demo;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.ForwardedHeaderFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@EnableCaching
@EnableProcessApplication
public class Application {
	
	private static final String CORS_CONFIG_URLS = "CORS_ALLOWED_ORGINS";

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("Authorization",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("camunda-starter-service").version("1.0.0").description(
						"<a target=\"_blank\" href=\"https://github.com/demo/camunda-starter-service\">Source Code</a> | "
								+ "<a target=\"_blank\" href=\"https://learning.postman.com/docs/integrations/available-integrations/working-with-openAPI\">Postman Importing OpenAPI Definition</a> using api-docs"));
	}

	// forwarding for swagger
	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration configurationLocal = new CorsConfiguration();
		configurationLocal.setAllowCredentials(true);
		configurationLocal.addAllowedHeader("*");
		configurationLocal.addAllowedMethod("*");
		
		String allowedOrigins = System.getenv(CORS_CONFIG_URLS);
		if (StringUtils.isNotBlank(allowedOrigins)) {
			configurationLocal.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
		}
		source.registerCorsConfiguration("/**", configurationLocal);

		return source;
	}

}

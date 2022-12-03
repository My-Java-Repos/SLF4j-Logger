package com.org.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This file replaces favicon-*.png, /swagger-ui/index.html, and custom .css and js files with a org branded OCM friendly alternatives.
 *
 * Copies what springdoc-openapi-ui module does with WebMvcConfigurerAdapter, in order to keep the compatibility for users using spring 4 or spring 5 versions.
 */
@Configuration
@SuppressWarnings("deprecation")
class SwaggerConf extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui/*.png", "/swagger-ui/*.html", "/swagger-ui/custom-swagger-*.js", "/swagger-ui/custom-swagger-*.css")
				.addResourceLocations("classpath:/swagger-ui/")
				.resourceChain(false);
	}

}

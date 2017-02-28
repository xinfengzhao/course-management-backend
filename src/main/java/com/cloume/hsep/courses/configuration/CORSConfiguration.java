package com.cloume.hsep.courses.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@EnableWebMvc
public class CORSConfiguration extends WebMvcConfigurerAdapter {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		
		registry.addMapping("/**")
			.allowedHeaders("x-requested-with", "origin", "accept", "content-type", "hsep-client-id", "Authorization")
			.allowedOrigins("*")
			.allowedMethods("GET", "PUT", "POST", "GET", "OPTIONS", "DELETE")
			;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources");
        super.addResourceHandlers(registry);
    }
}

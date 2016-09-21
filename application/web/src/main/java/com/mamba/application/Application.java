package com.mamba.application;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mamba.framework.boot.ApplicationDruid;
import com.mamba.framework.boot.ApplicationMonitoring;

@SpringBootApplication
@Import({ ApplicationScheduling.class 
    , ApplicationMonitoring.class
    ,ApplicationDruid.class})
public class Application extends SpringBootServletInitializer {
	@Bean
	public WebMvcConfigurer corsConfigurer() { // 允许跨域
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("POST", "DELETE", "PUT", "GET",
						"OPTIONS");
			}
		};
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Application.class, args);
	}
}

package net.gundul.interkom_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	private final String frontendOrigin;

	public WebConfig(
			@Value("${app.cors.frontend-uri}") String frontendUri,
			@Value("${app.cors.frontend-port}") String frontendPort
	)
	{
		this.frontendOrigin = frontendUri + ':' + frontendPort;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Allow CORS for all endpoints
				.allowedOrigins(frontendOrigin)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true);
	}
}

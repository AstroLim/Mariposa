package com.gabriel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images from external resources folder
        // Images are located at: C:\Users\Evan\Desktop\MARIPOSA\resources\rice-pics\
        // This path is relative to the project root (MARIPOSA folder)
        java.io.File resourcesDir = Paths.get("resources", "rice-pics").toAbsolutePath().toFile();
        String resourcesPath = resourcesDir.toURI().toString();
        
        // Add all possible locations - Spring will check them in order
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                    resourcesPath,                    // Primary: external resources folder (uses file:// URI)
                    "classpath:/static/images/",     // Fallback 1: standard Spring Boot location
                    "classpath:/images/"             // Fallback 2: direct resources location
                );
    }
}


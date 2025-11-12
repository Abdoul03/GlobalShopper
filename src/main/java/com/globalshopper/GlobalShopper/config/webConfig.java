package com.globalshopper.GlobalShopper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Toutes les requêtes /images/** pointeront vers le dossier physique
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/tuwindi/Desktop/uploads/");

    }
}

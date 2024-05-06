package com.example.Newsternews.DatabaseManagement;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Fix CORS Error
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
        .allowedMethods("GET","POST")
        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept","Access-Control-Allow-Credentials")
        .allowCredentials(true)
        .allowedOrigins("http://localhost:3000");
    }
}

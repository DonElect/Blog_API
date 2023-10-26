package com.donatus.fashion_blog_api.config.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dfjzskbe6";
    private final String API_KEY = "473221831389556";
    private final String API_SECRET = "Yy8o03dCGqc219TUQCV9OQJl1Wc";
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);

        return new Cloudinary(config);
    }
}

package com.nam.image_service.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<Object, Object> config = new HashMap<>();
        config.put("cloud_name", "dfkjoymea");
        config.put("api_key", "762736894688695");
        config.put("api_secret", "GEfpiMzOArRtGxE0CtIkx3p_NlA");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}

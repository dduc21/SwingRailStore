package com.example.demo.srt.sd77.infrastructure.configs.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigCloudinary {

    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ddb4ozxhi",
                "api_key", "391612231874744",
                "api_secret", "2-lznaD0zMOIIwx4nxvZK3rXJAg",
                "secure", true));
    }
}

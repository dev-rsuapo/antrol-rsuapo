package com.rsuapo.antrol.api;

import com.rsuapo.antrol.library.service.AntrolService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ComRsuapoAntrolApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComRsuapoAntrolApiApplication.class, args);
    }

    @Value("${antrol.baseUrl}")
    private String baseUrl;
    @Value("${antrol.consumerId}")
    private String consumerId;
    @Value("${antrol.consumerSecret}")
    private String consumerSecret;
    @Value("${antrol.userKey}")
    private String userKey;

    @Bean
    public AntrolService antrolService() {
        AntrolService antrolService = new AntrolService.Builder()
                .baseUrl(baseUrl)
                .consumerId(consumerId)
                .consumerSecret(consumerSecret)
                .userKey(userKey)
                .build();
        return antrolService;
    }
    
}

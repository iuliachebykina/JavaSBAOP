package com.example.aop;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class ApiConfig {

    private ConcurrentHashMap<String, Integer> map;
    @Bean
    public Calls calls(){
        return new Calls(map);
    }
}

package com.autobots.automanager.configuracao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguracao {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

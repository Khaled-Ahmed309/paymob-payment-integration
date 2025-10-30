package com.ecommerce.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${BASE_PAYMOB_URL}")
    private String BASE_PAYMOB_URL;
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(BASE_PAYMOB_URL)
                .build();
    }
}

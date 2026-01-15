package com.synesthesia.spring_payment_gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Configuration
@Slf4j
public class RestClientConfig {

    @Value("${xendit.api.url}")
    private String xenditApiUrl;
    @Value("${xendit.api.version}")
    private String xenditApiVersion;
    @Value("${xendit.api.key}")
    private String xenditApiKey;

    @Bean
    public RestClient xenditClient(RestClient.Builder builder) {
        return builder
                .baseUrl(xenditApiUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("api-version", xenditApiVersion)
                .defaultHeader(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64.getEncoder()
                                .encodeToString((xenditApiKey).getBytes()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}


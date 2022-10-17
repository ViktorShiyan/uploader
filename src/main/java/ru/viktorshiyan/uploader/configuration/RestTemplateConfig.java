package ru.viktorshiyan.uploader.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигурация RestTemplate
 *
 * @author Viktor Shiyan
 * @since 17.10.2022
 */
@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate beanRestTemplate() {
        return restTemplateBuilder.build();
    }
}

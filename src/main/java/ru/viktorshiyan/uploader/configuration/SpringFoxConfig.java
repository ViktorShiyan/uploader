package ru.viktorshiyan.uploader.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Конфигурация для OpenApi
 *
 * @author Viktor Shiyan
 * @since 14.10.2022
 */
@Configuration
@EnableSwagger2
@AllArgsConstructor
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.viktorshiyan.uploader.controllers"))
                .paths(PathSelectors.any())
                .build();
    }
}

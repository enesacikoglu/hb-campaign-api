package com.company.campaign.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final SwaggerConfigurationProperties swaggerConfigurationProperties;

    public SwaggerConfiguration(SwaggerConfigurationProperties swaggerConfigurationProperties) {
        this.swaggerConfigurationProperties = swaggerConfigurationProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).host(swaggerConfigurationProperties.getHost())
                .pathMapping(swaggerConfigurationProperties.getPath())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.company.campaign.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .protocols(new HashSet<>(Arrays.asList("https", "http")));
    }

}

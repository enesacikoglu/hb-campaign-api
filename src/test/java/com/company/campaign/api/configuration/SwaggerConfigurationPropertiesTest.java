package com.company.campaign.api.configuration;

import org.junit.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class SwaggerConfigurationPropertiesTest {

    @Configuration
    @EnableConfigurationProperties(SwaggerConfigurationProperties.class)
    static class Config {
    }

    private ApplicationContextRunner runner =
            new ApplicationContextRunner()
                    .withUserConfiguration(Config.class)
                    .withPropertyValues("swagger.host=localhost:8080", "swagger.path=swagger_path");

    @Test
    public void it_should_load_configuration_properties() {
        runner.run(context -> {
            //Given
            SwaggerConfigurationProperties swaggerConfigurationProperties = context.getBean(SwaggerConfigurationProperties.class);

            //When
            String swaggerHost = swaggerConfigurationProperties.getHost();
            String swaggerPath = swaggerConfigurationProperties.getPath();

            //Then
            assertThat(swaggerHost).isEqualTo("localhost:8080");
            assertThat(swaggerPath).isEqualTo("swagger_path");
        });
    }

}
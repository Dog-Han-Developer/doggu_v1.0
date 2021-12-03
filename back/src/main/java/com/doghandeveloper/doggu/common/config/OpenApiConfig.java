package com.doghandeveloper.doggu.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion, @Value("${server.servlet.context-path}") String contextPath) {
        Info info = new Info().title("Doggu").version(appVersion)
                .description("Doggu 서비스를 위한 웹 애플리케이션 API입니다.")
                .termsOfService("https://swagger.io/terms/")
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        Components components = new Components().addSecuritySchemes("Authorization",new SecurityScheme().name("Authorization").type(SecurityScheme.Type.HTTP).scheme("bearer"));


        return new OpenAPI()
                .addServersItem(new Server().url(contextPath))
                .components(components)
                .info(info);
    }
}
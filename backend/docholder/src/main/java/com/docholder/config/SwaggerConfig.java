package com.docholder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

// localhost:8082/swagger-ui/
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.docholder.controllers"))
//                .paths(PathSelectors.regex(".*/user/.*"))
//                .paths(PathSelectors.regex(".*/company/.*"))
//                .paths(regex(""))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("DocHolder API documentation")
                .description("Use this documentation as a reference how to interact with app API")
                .version("0.1 beta")
                .contact(new Contact("Trainee's project", "http://spb.edu-netcracker.com", "example@mail.ru"))
                .build();
    }


}
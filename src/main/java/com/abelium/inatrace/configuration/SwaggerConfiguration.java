package com.abelium.inatrace.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerSpringBean() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        
        securitySchemes.add(new ApiKey("X-AUTH-TOKEN", "X-AUTH-TOKEN", "header"));
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(securitySchemes)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(Predicates.equalTo("/")))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(CompletableFuture.class);
    }

    // API Info as it appears on the swagger-ui page
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("INATrace Services API")
                .description("Abelium INATrace Services API swagger documentation")
                .license("INATrace")
                .licenseUrl("")
                .version("1.0")
                .build();        
    }

}

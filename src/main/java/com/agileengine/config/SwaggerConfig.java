package com.agileengine.config;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.base.Predicates.not;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket agileEngineApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(getApiInfo())
                .select()
                .paths(getPathsSelector())
                .apis(filterSpringBootEndpoints())
                .build();
        docket.protocols(Collections.singleton("http"));
        return docket;
    }

    private Predicate<String> getPathsSelector() {
        return not(PathSelectors.regex("/error"));
    }

    private Predicate<RequestHandler> filterSpringBootEndpoints() {
        return Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "AgileEngine app",
                "AgileEngine interview app",
                "1.0",
                "https://www.linkedin.com/in/matias-prado-2b6811142//",
                new Contact("Matias Prado", "https://www.linkedin.com/in/matias-prado-2b6811142/", "matiprado92@igmail.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }

}

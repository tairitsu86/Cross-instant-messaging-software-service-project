package com.cimss.project.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

//json file: http://localhost:8080/v3/api-docs
//swagger file: http://localhost:8080/swagger-ui/index.html#/
//yaml file: http://localhost:8080/v3/api-docs.yaml
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Cross-instant messaging software microservices API")
                        .description("Cross-instant messaging software microservices Restful API")
                        .version("v0.0.2")
//                        .termsOfService("https://www.jianshu.com/u/c8c1e5ac61e0")
//                        .license(new License().name("****").url("https://www.jiahetng.com"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project introduction")
                        .url("https://hackmd.io/sUxf18wWTHCNEO4dYwu4sg?view#microservice-instant-message-%E4%BB%8B%E7%B4%B9%E8%88%87%E6%9B%B4%E6%96%B0%E6%97%A5%E8%AA%8C"));
    }
    @Bean
    public GroupedOpenApi CIMSSApi() {
        return GroupedOpenApi.builder()
                .group("CIM-api")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    if (handlerMethod.getBeanType().getSimpleName().equals("LineMessageHandlerSupport")) {
                        return null;
                    }
                    return operation;
                })
                .build();
    }


}

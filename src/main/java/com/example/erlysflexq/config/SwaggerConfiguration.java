package com.example.erlysflexq.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("测试模板");
        ApiInfo apiInfo = apiInfoBuilder.build();
        docket.apiInfo(apiInfo);

        ApiSelectorBuilder apiSelectorBuilder = docket.select();
        apiSelectorBuilder.paths(PathSelectors.any());
        apiSelectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        docket = apiSelectorBuilder.build();

        ApiKey apiKey = new ApiKey("token", "token", "headers");
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(apiKey);
        docket.securitySchemes(apiKeyList);

        AuthorizationScope scope = new AuthorizationScope("global",
                "accessEverything");
        AuthorizationScope[] scopes = {scope};

        SecurityReference securityReference = new SecurityReference("token",
                scopes);
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(securityReference);

        SecurityContext securityContext =
                SecurityContext.builder().securityReferences(securityReferenceList).build();
        List<SecurityContext> securityContextList = new ArrayList<>();
        securityContextList.add(securityContext);

        docket.securityContexts(securityContextList);

        return docket;
    }

}

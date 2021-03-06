package com.company.itinerary.management.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.itinerary.management.constants.ItineraryConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
    	
    	List<ResponseMessage> responseMessages = Arrays.asList(
                new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message(ItineraryConstants.INVALID_INPUT).build(),
                new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value()).message(ItineraryConstants.NOT_AUTHORIZED).build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ItineraryConstants.GENERAL_ERROR).build());
    	
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
        		.ignoredParameterTypes(Authentication.class)
        		.globalOperationParameters(
                        Stream.of(new ParameterBuilder()
                                .name("Authorization")
                                .description(ItineraryConstants.API_TOKEN_DESCRIPTION)
                                .defaultValue("Basic YWRtaW46YWRtaW5AUGFzc3dvcmRAMQ==")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(true)
                                .build()).collect(Collectors.toList()))
        		.select()
            .apis(RequestHandlerSelectors
                .basePackage("com.company.itinerary.management.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo())
            .globalResponseMessage(RequestMethod.POST, responseMessages)
            .globalResponseMessage(RequestMethod.PUT, responseMessages)
            .globalResponseMessage(RequestMethod.GET, responseMessages)
            .globalResponseMessage(RequestMethod.DELETE, responseMessages);
    }
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Itinerary Management Service REST API")
            .description("Itinerary Management Service REST API")
            .version("1.0.0")
            .build();
    }
}
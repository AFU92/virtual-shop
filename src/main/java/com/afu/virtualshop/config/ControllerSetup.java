package com.afu.virtualshop.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ControllerSetup {

	private static final String API_VERSION = "1.0";
	private static final String API_TITLE = "Virtual Shop";
	private static final String API_DESCRIPTION =
			"A REST API";

	/**
	 * Configures Swagger
	 *
	 * @return a {@link Docket} instance
	 */
	@Bean public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(getPaths())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false);
	}

	/**
	 * Returns an abstraction of the basic API info
	 * in order to include it into the API documentation
	 *
	 * @return an {@link ApiInfo} instance
	 */
	private ApiInfo apiInfo() {

		return new ApiInfoBuilder()
				.title(API_TITLE)
				.description(API_DESCRIPTION)
				.version(API_VERSION)
				.build();
	}

	private Predicate<String> getPaths() {

		return Predicates.not(PathSelectors.regex("/error"));
	}

}

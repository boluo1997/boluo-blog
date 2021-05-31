package com.boluo.blog.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;

/**
 * <p>
 * swagger页面展示的配置
 * </p>
 *
 * @author Toby
 * @since 2020/5/13
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	@Bean
	public Docket createRestApi() {
		HashSet<String> contentType = new HashSet<>(1);
		contentType.add("application/json");
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.consumes(contentType)
				.produces(contentType)
				.select()
				// 扫描包路径
				.apis(RequestHandlerSelectors.basePackage("com.boluo.blog.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	// 路径: http://localhost:8848/swagger-ui.html
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("boluo-api文档")
				.description("xxx-xx模块api文档")
				.version("1.0")
				.contact(new Contact("菠萝吹雪项目组","www.google.com","boluo29511@163.com"))
				.build();
	}


}
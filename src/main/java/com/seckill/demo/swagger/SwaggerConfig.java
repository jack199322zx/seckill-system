package com.seckill.demo.swagger;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("这是一个秒杀系统").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.seckill.demo.controller")).paths(PathSelectors.any()).build();
	}
	// 预览地址:swagger-ui.html
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("首次使用Swagger2构建文档").termsOfServiceUrl("https://www.52nino.cn")
				.contact(new Contact("尼诺的博客 ", "https://www.52nino.cn", "461333622@qq.com")).version("1.1").build();
	}
}

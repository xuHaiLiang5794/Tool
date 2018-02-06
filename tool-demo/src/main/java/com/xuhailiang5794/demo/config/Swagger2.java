package com.xuhailiang5794.demo.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 *
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/6 15:47
 */
@EnableSwagger2
@EnableWebMvc
@Configuration
public class Swagger2 extends WebMvcConfigurationSupport {
    public Docket docket() {
        ParameterBuilder builder = new ParameterBuilder();

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfoYunTu()).select()
                .apis(RequestHandlerSelectors.basePackage("com.xuhailiang5794"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }

    private ApiInfo apiInfoYunTu() {
        return new ApiInfoBuilder().title("Rest API").version("1.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").license("Apache 2.0")
                .description("此API为开发人员，测试人员，UI人员提供方便快捷的API开发，测试体验").build();
    }

    @Configuration
    public class SwaggerWebConfigurer extends WebMvcConfigurerAdapter {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            super.addResourceHandlers(registry);
        }
    }
}

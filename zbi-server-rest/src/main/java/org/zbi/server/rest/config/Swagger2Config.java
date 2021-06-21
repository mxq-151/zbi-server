package org.zbi.server.rest.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger的配置对象
 */
@Configuration
@EnableSwagger2
public abstract class Swagger2Config {

    /**
     * swagger2的配置对象
     * 把一个配置好的docket,(swagger2的配置对象) 交给springboot来管理
     *
     * @return
     */
    @Bean
    public Docket apiConnfig() {
        Docket build = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
        List<Parameter> parameters = setHeader();
        if (parameters != null && parameters.size() > 0) {
            build.globalOperationParameters(parameters);
        }
        return build;
    }

    protected abstract ApiInfo apiInfo();

    protected abstract List<Parameter> setHeader();
}
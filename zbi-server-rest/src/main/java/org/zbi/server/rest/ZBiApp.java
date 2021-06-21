package org.zbi.server.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 */
@SpringBootApplication
@ComponentScan({"org.zbi"})
@EnableSwagger2
@MapperScan("org.zbi.server.mapper.mysql")
public class ZBiApp {
    public static void main(String[] args) {
        SpringApplication.run(ZBiApp.class, args);
    }

}

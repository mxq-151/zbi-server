package com.zbi.commons.server.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class UserSessionConfigAdapter extends WebMvcConfigurerAdapter {

    @Bean
    public UserSessionInterceptor userSessionInterceptor() {
        return new UserSessionInterceptor();
    }

    /**
     * 增加系统拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userSessionInterceptor())
                .excludePathPatterns("/user/login", "/bluewhale/index", "/swagger**/**", "/user/opiskvpaeb", "/cas/**")
                .addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}

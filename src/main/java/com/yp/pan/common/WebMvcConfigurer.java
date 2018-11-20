package com.yp.pan.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * WebMvcConfigurer class
 *
 * @author Administrator
 * @date 2018/11/20
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

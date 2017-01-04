package com.xecoder.config;

import com.xecoder.common.interceptor.AuthInterceptor;
import com.xecoder.common.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by vincent on 1/4/17.
 */

@Configuration
public class SystemInterceptors extends WebMvcConfigurerAdapter {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/api/**");//认证
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");//日志
    }
}

package com.xecoder.config;

import com.xecoder.common.exception.SysException;
import com.xecoder.common.exception.handler.SysRestExceptionHandler;
import com.xecoder.common.interceptor.AuthInterceptor;
import com.xecoder.common.interceptor.LogInterceptor;
import com.xecoder.common.interceptor.ResponseCachedFilter;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Created by vincent on 1/4/17.
 */

@Configuration
@EnableWebMvc
public class SystemInterceptors extends WebMvcConfigurerAdapter {


    @Resource
    private MessageSource messageSource;

    @Bean
    public RestHandlerExceptionResolver restExceptionResolver() {
        return RestHandlerExceptionResolver.builder()
                .messageSource(messageSource)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .addErrorMessageHandler(EmptyResultDataAccessException.class, HttpStatus.NOT_FOUND)
                .addHandler(SysException.class, new SysRestExceptionHandler<>(SysException.class,HttpStatus.ACCEPTED))
                .build();
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }


    @Bean
    public ResponseCachedFilter responseCachedFilter() {
        return new ResponseCachedFilter();
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

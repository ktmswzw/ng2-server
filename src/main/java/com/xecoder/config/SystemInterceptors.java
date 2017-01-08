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
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by vincent on 1/4/17.
 */

@Configuration
@EnableWebMvc
public class SystemInterceptors extends WebMvcConfigurerAdapter {

    @Bean  // Magic entry
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(new RestJackson2HttpMessageConverter());
    }

    @Bean
    public RestHandlerExceptionResolver restExceptionResolver() {
        return RestHandlerExceptionResolver.builder()
                .messageSource(httpErrorMessageSource())
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
    public MessageSource httpErrorMessageSource() {
        ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();
        m.setBasename("classpath:messages");
        m.setDefaultEncoding("UTF-8");
        return m;
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
        registry.addInterceptor(logInterceptor()).addPathPatterns("/api/**");//日志
    }
}

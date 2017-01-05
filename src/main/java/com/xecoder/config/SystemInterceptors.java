package com.xecoder.config;

import com.xecoder.common.exception.HabitException;
import com.xecoder.common.exception.HabitRestExceptionHandler;
import com.xecoder.common.interceptor.AuthInterceptor;
import com.xecoder.common.interceptor.LogInterceptor;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Created by vincent on 1/4/17.
 */

@Configuration
@EnableWebMvc
public class SystemInterceptors extends WebMvcConfigurerAdapter {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        //设置默认区域,
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }

    @Resource
    private MessageSource messageSource;

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        return lci;
    }
    @Bean
    public RestHandlerExceptionResolver restExceptionResolver() {
        return RestHandlerExceptionResolver.builder()
                .messageSource(messageSource)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .addErrorMessageHandler(EmptyResultDataAccessException.class, HttpStatus.NOT_FOUND)
                .addHandler(HabitException.class, new HabitRestExceptionHandler())
                .build();
    }

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
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(authInterceptor()).addPathPatterns("/api/**");//认证
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");//日志
    }
}

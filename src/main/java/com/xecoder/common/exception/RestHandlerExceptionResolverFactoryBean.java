/*
 * Copyright 2014 Jakub Jirutka <jakub@jirutka.cz>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xecoder.common.exception;

import cz.jirutka.spring.exhandler.handlers.RestExceptionHandler;
import cz.jirutka.spring.exhandler.interpolators.MessageInterpolator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.accept.ContentNegotiationManager;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Component
@ConfigurationProperties(locations = "classpath:messages.properties")
public class RestHandlerExceptionResolverFactoryBean implements FactoryBean<RestHandlerExceptionResolver> {

    /**
     * The {@link org.springframework.web.accept.ContentNegotiationManager} to use to resolve acceptable media types.
     * If not provided, the default instance of {@code ContentNegotiationManager} with
     * {@link org.springframework.web.accept.HeaderContentNegotiationStrategy HeaderContentNegotiationStrategy}
     * and {@link org.springframework.web.accept.FixedContentNegotiationStrategy FixedContentNegotiationStrategy}
     * (with {@link #setDefaultContentType(String) defaultContentType}) will be used.
     */
    private ContentNegotiationManager contentNegotiationManager;

    /**
     * The default content type that will be used as a fallback when the requested content type is
     * not supported.
     */
    private String defaultContentType;

    /**
     * Mapping of exception handlers where the key is an exception type to handle and the value is
     * either a HTTP status (this will register
     * {@link cz.jirutka.spring.exhandler.handlers.ErrorMessageRestExceptionHandler
     * ErrorMessageRestExceptionHandler}) and/or an instance of the {@link cz.jirutka.spring.exhandler.handlers.RestExceptionHandler}.
     * <p/>
     * <p>Each handler is also used for all the exception subtypes, when no more specific mapping
     * is found.</p>
     * <p/>
     * <p><b>Example:</b>
     * <pre>{@code
     * <property name="exceptionHandlers">
     *     <map>
     *         <entry key="org.springframework.dao.EmptyResultDataAccessException" value="404" />
     *         <entry key="org.example.MyException">
     *             <bean class="org.example.MyExceptionHandler" />
     *         </entry>
     *     </map>
     * </property>
     * }</pre>
     */
    private Map<Class<? extends Exception>, ?> exceptionHandlers = emptyMap();

    /**
     * The message body converters to use for converting an error message into HTTP response body.
     * If not provided, the default converters will be used (see
     * {@link cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils#getDefaultHttpMessageConverters()
     * getDefaultHttpMessageConverters()}).
     */
    private List<HttpMessageConverter<?>> httpMessageConverters;

    /**
     * The message interpolator to set into all exception handlers implementing
     * {@link cz.jirutka.spring.exhandler.interpolators.MessageInterpolatorAware}
     * interface, e.g. {@link cz.jirutka.spring.exhandler.handlers.ErrorMessageRestExceptionHandler}.
     * Built-in exception handlers uses {@link cz.jirutka.spring.exhandler.interpolators.SpelMessageInterpolator
     * SpelMessageInterpolator} by default.
     */
    private MessageInterpolator messageInterpolator;

    /**
     * The message source to set into all exception handlers implementing
     * {@link org.springframework.context.MessageSourceAware MessageSourceAware} interface, e.g.
     * {@link cz.jirutka.spring.exhandler.handlers.ErrorMessageRestExceptionHandler}.
     * Required for built-in exception handlers.
     */
    private MessageSource messageSource;

    /**
     * Whether to register default exception handlers for Spring exceptions. These are registered
     * <i>before</i> the provided exception handlers, so you can overwrite any of the default
     * mappings. Default is <tt>true</tt>.
     */
    private boolean withDefaultHandlers = true;

    /**
     * Whether to use the default (built-in) message source as a fallback to resolve messages that
     * the provided message source can't resolve. In other words, it sets the default message
     * source as a <i>parent</i> of the provided message source. Default is <tt>true</tt>.
     */
    private boolean withDefaultMessageSource = true;


    @SuppressWarnings("unchecked")
    public RestHandlerExceptionResolver getObject() {

        RestHandlerExceptionResolverBuilder builder = createBuilder();
        builder.setMessageSource(messageSource);
        builder.setMessageInterpolator(messageInterpolator);
        builder.setHttpMessageConverters(httpMessageConverters);
        builder.setContentNegotiationManager(contentNegotiationManager);
        builder.defaultContentType(defaultContentType);
        builder.setWithDefaultHandlers(withDefaultHandlers);
        builder.setWithDefaultMessageSource(withDefaultMessageSource);

        for (Map.Entry<Class<? extends Exception>, ?> entry : exceptionHandlers.entrySet()) {
            Class<? extends Exception> exceptionClass = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof RestExceptionHandler) {
                builder.addHandler(exceptionClass, (RestExceptionHandler) value);

            } else {
                builder.addErrorMessageHandler(exceptionClass, parseHttpStatus(value));
            }
        }

        return builder.build();
    }

    public Class<?> getObjectType() {
        return RestHandlerExceptionResolver.class;
    }

    public boolean isSingleton() {
        return false;
    }


    RestHandlerExceptionResolverBuilder createBuilder() {
        return RestHandlerExceptionResolver.builder();
    }

    HttpStatus parseHttpStatus(Object value) {
        Assert.notNull(value, "Values of the exceptionHandlers map must not be null");

        if (value instanceof HttpStatus) {
            return (HttpStatus) value;

        } else if (value instanceof Integer) {
            return HttpStatus.valueOf((int) value);

        } else if (value instanceof String) {
            return HttpStatus.valueOf(Integer.valueOf((String) value));

        } else {
            throw new IllegalArgumentException(String.format(
                    "Values of the exceptionHandlers maps must be instance of ErrorResponseFactory, HttpStatus, " +
                            "String, or int, but %s given", value.getClass()));
        }
    }

    public void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager) {
        this.contentNegotiationManager = contentNegotiationManager;
    }

    public void setDefaultContentType(String defaultContentType) {
        this.defaultContentType = defaultContentType;
    }

    public void setExceptionHandlers(Map<Class<? extends Exception>, ?> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    public void setHttpMessageConverters(List<HttpMessageConverter<?>> httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
    }

    public void setMessageInterpolator(MessageInterpolator messageInterpolator) {
        this.messageInterpolator = messageInterpolator;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setWithDefaultHandlers(boolean withDefaultHandlers) {
        this.withDefaultHandlers = withDefaultHandlers;
    }

    public void setWithDefaultMessageSource(boolean withDefaultMessageSource) {
        this.withDefaultMessageSource = withDefaultMessageSource;
    }
}

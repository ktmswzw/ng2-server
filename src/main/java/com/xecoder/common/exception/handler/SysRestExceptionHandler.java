package com.xecoder.common.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.xecoder.common.exception.SysException;
import com.xecoder.common.exception.SystemErrorMsg;
import cz.jirutka.spring.exhandler.interpolators.MessageInterpolator;
import cz.jirutka.spring.exhandler.interpolators.MessageInterpolatorAware;
import cz.jirutka.spring.exhandler.interpolators.NoOpMessageInterpolator;
import cz.jirutka.spring.exhandler.interpolators.SpelMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SysRestExceptionHandler<E extends Exception> extends SysAbstractRestExceptionHandler<E, SystemErrorMsg> implements MessageSourceAware, MessageInterpolatorAware {

    private static final Logger LOG = LoggerFactory.getLogger(SysRestExceptionHandler.class);
    protected static final String
            DEFAULT_PREFIX = "default",
            ERROR_MSG = "error",
            ERROR_MSG_EN = "error_en",
            ERROR_CODE = "error_code";

    private MessageSource messageSource;

    private MessageInterpolator interpolator = new SpelMessageInterpolator();

    public SysRestExceptionHandler(Class<E> exceptionClass, HttpStatus status) {
        super(exceptionClass, status);
    }

    protected SysRestExceptionHandler(HttpStatus status) {
        super(status);
    }


    public SystemErrorMsg createBody(E ex, HttpServletRequest req) {

        SystemErrorMsg m = new SystemErrorMsg();

        JSONObject result = new JSONObject();

        if (ex instanceof SysException) {
            result.put("status",((SysException) ex).getFactor().getHttpStatus());
        } else {
            result.put("status",getStatus());
        }
        result.put("error",(resolveMessage(ERROR_MSG, ex, req)));
        result.put("error_en",(resolveMessage(ERROR_MSG_EN, ex, req)));
        result.put("path",(req.getRequestURI()));
        result.put("error_code",(resolveMessage(ERROR_CODE, ex, req)));
        m.setResult(result);
        return m;
    }


    protected String resolveMessage(String key, E exception, HttpServletRequest request) {

        String template = getMessage(key, exception, LocaleContextHolder.getLocale());

        Map<String, Object> vars = new HashMap<String, Object>(2);
        vars.put("ex", exception);
        vars.put("req", request);

        return interpolateMessage(template, vars);
    }

    protected String interpolateMessage(String messageTemplate, Map<String, Object> variables) {
        LOG.trace("Interpolating message '{}' with variables: {}", messageTemplate, variables);
        return interpolator.interpolate(messageTemplate, variables);
    }

    protected String getMessage(String key, Exception ex, Locale locale) {

        String message = null;
        String prefix = null;

        if (ex instanceof SysException) {
            message = messageSource.getMessage(((SysException) ex).getFactor().getName() + "." + key, null, null, locale);
        } else {
            prefix = getExceptionClass().getName();
            message = messageSource.getMessage(prefix + "." + key, null, null, locale);
        }
        if (message == null) {
            message = messageSource.getMessage(DEFAULT_PREFIX + "." + key, null, null, locale);
        }
        if (message == null) {
            message = messageSource.getMessage("error.system." + key, null, null, locale);
            LOG.error(ex.getMessage());
        }
        return message;
    }


    ////// Accessors //////

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource must not be null");
        this.messageSource = messageSource;
    }

    public void setMessageInterpolator(MessageInterpolator interpolator) {
        this.interpolator = interpolator != null ? interpolator : new NoOpMessageInterpolator();
    }
}


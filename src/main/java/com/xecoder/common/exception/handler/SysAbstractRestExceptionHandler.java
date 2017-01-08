package com.xecoder.common.exception.handler;

import cz.jirutka.spring.exhandler.handlers.RestExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.core.GenericTypeResolver.resolveTypeArguments;

public abstract class SysAbstractRestExceptionHandler<E extends Exception, T> extends ResponseEntityExceptionHandler implements RestExceptionHandler<E, T> {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    private final Class<E> exceptionClass;
    private final HttpStatus status;


    /**
     * This constructor determines the exception class from the generic class parameter {@code E}.
     *
     * @param status HTTP status
     */
    protected SysAbstractRestExceptionHandler(HttpStatus status) {
        this.exceptionClass = determineTargetType();
        this.status = status;
        LOG.trace("Determined generic exception type: {}", exceptionClass.getName());
    }

    protected SysAbstractRestExceptionHandler(Class<E> exceptionClass, HttpStatus status) {
        this.exceptionClass = exceptionClass;
        this.status = status;
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("path",request.getContextPath());
        responseBody.put("message","The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<Object>(responseBody,HttpStatus.NOT_FOUND);
    }
    ////// Abstract methods //////

    public abstract T createBody(E ex, HttpServletRequest req);


    ////// Template methods //////

    public ResponseEntity<T> handleException(E ex, HttpServletRequest req) {

        logException(ex, req);

        T body = createBody(ex, req);
        HttpHeaders headers = createHeaders(ex, req);

        //兼容api，将所有返回http code 设置为200
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    public Class<E> getExceptionClass() {
        return exceptionClass;
    }

    public HttpStatus getStatus() {
        return status;
    }


    protected HttpHeaders createHeaders(E ex, HttpServletRequest req) {
        return new HttpHeaders();
    }

    /**
     * Logs the exception; on ERROR level when status is 5xx, otherwise on INFO level without stack
     * trace, or DEBUG level with stack trace. The logger name is
     * {@code cz.jirutka.spring.exhandler.handlers.RestExceptionHandler}.
     *
     * @param ex The exception to log.
     * @param req The current web request.
     */
    protected void logException(E ex, HttpServletRequest req) {

        if (LOG.isErrorEnabled() && getStatus().value() >= 500 || LOG.isInfoEnabled()) {
            Marker marker = MarkerFactory.getMarker(ex.getClass().getName());

            String uri = req.getRequestURI();
            if (req.getQueryString() != null) {
                uri += '?' + req.getQueryString();
            }
            String msg = String.format("%s %s ~> %s", req.getMethod(), uri, getStatus());

            if (getStatus().value() >= 500) {
                LOG.error(marker, msg, ex);

            } else if (LOG.isDebugEnabled()) {
                LOG.debug(marker, msg, ex);

            } else {
                LOG.info(marker, msg);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Class<E> determineTargetType() {
        return (Class<E>) resolveTypeArguments(getClass(), SysAbstractRestExceptionHandler.class)[0];
    }
}

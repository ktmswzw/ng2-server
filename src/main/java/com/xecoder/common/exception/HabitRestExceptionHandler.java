package com.xecoder.common.exception;

import com.xecoder.common.utils.LocaleMessageSourceUtil;
import cz.jirutka.spring.exhandler.handlers.RestExceptionHandler;
import cz.jirutka.spring.exhandler.interpolators.MessageInterpolator;
import cz.jirutka.spring.exhandler.interpolators.SpelMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HabitRestExceptionHandler implements RestExceptionHandler<HabitException, String> {

    private static final Logger LOG = LoggerFactory.getLogger(HabitRestExceptionHandler.class);
    protected static final String
            ERROR_MSG = "error",
            ERROR_MSG_EN = "error_en",
            ERROR_CODE = "error_code";

    private HttpStatus status;


    @Autowired
    private LocaleMessageSourceUtil messageSourceUtil;

    private MessageInterpolator interpolator = new SpelMessageInterpolator();

    @Override
    public ResponseEntity<String> handleException(HabitException exc, HttpServletRequest arg1) {
        this.status = exc.getStatus();
//        return ResponseEntity.status(exc.getStatus()).body(exc.getMessage());


        SystemErrorMsg body = createBody(exc, arg1);
        HttpHeaders headers = new HttpHeaders();

        //兼容api，将所有返回http code 设置为200
        return new ResponseEntity(body, headers, HttpStatus.OK);
    }


    public HttpStatus getStatus() {
        return status;
    }

    public SystemErrorMsg createBody(HabitException ex, HttpServletRequest req) {

        SystemErrorMsg m = new SystemErrorMsg();
        m.setStatus(getStatus());
        m.setError(resolveMessage(ERROR_MSG, ex, req));
        m.setErrorEn(resolveMessage(ERROR_MSG_EN, ex, req));
        m.setPath(req.getRequestURI());
        m.setErrorCode(resolveMessage(ERROR_CODE, ex, req));
        return m;
    }


    protected String resolveMessage(String key, HabitException exception, HttpServletRequest request) {

        String template = messageSourceUtil.getMessage(key);

        Map<String, Object> vars = new HashMap<String, Object>(2);
        vars.put("ex", exception);
        vars.put("req", request);

        return interpolateMessage(template, vars);
    }

    protected String interpolateMessage(String messageTemplate, Map<String, Object> variables) {
        LOG.trace("Interpolating message '{}' with variables: {}", messageTemplate, variables);
        return interpolator.interpolate(messageTemplate, variables);
    }

}


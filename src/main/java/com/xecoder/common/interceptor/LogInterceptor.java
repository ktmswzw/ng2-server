package com.xecoder.common.interceptor;

import com.xecoder.common.exception.SysLogger;
import com.xecoder.common.utils.IPUtils;
import com.xecoder.common.utils.JWTCode;
import com.xecoder.config.BaseController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogInterceptor implements HandlerInterceptor {

    Date beginTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        beginTime = new Date();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String uid;
        String result;
        HandlerMethod method = (HandlerMethod) handler;
        try {
            BaseController baseController = (BaseController) method.getBean();
            ContentResposeWrapper wrapper = (ContentResposeWrapper) response;
            uid = baseController.getUserId();
            result = new String(wrapper.getContentAsByteArray());
        } catch (Exception e) {
            uid = "500";
            result = "null";
        }
        Date now = new Date();

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
        String action = request.getRequestURI();
        String version = request.getHeader(BaseController.VERSION_STR);
        String jwt = request.getHeader(JWTCode.AUTHORIZATION_STR);
        String ip = IPUtils.getRealIpAddr(request);
        SysLogger.request("{} {} {} {} {} {} {} {} {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginTime), time, action, jwt, uid, request.getParameterMap(), version, ip, result);
    }
}
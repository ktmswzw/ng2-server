package com.xecoder.common.interceptor;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseCachedFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        if (!isAsyncDispatch(request) && !(response instanceof ContentResposeWrapper)) {
            response = new ContentResposeWrapper(response);
        }
        filterChain.doFilter(request, response);
        ContentResposeWrapper wrapper = (ContentResposeWrapper) response;
        wrapper.copyBodyToResponse();
    }
}

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
        HttpServletResponse responseToUse = response;
        responseToUse.setHeader("Access-Control-Allow-Origin", "*");
        responseToUse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, PUT");
        responseToUse.setHeader("Access-Control-Max-Age", "3600");
        responseToUse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept");

        if (!isAsyncDispatch(request) && !(response instanceof ContentResposeWrapper)) {
            responseToUse = new ContentResposeWrapper(response);
        }
        filterChain.doFilter(request, responseToUse);
        ContentResposeWrapper wrapper = (ContentResposeWrapper) responseToUse;
        wrapper.copyBodyToResponse();
    }
}

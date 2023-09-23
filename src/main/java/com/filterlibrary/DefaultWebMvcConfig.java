package com.filterlibrary;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

public class DefaultWebMvcConfig implements WebMvcConfigurer {
    private final List<HandlerInterceptor> handlerInterceptorList = new ArrayList<>();

    public DefaultWebMvcConfig(List<HandlerInterceptor> handlerInterceptorList) {
        for (HandlerInterceptor handlerInterceptor : handlerInterceptorList) {
            this.handlerInterceptorList.add(handlerInterceptor);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        for (HandlerInterceptor handlerInterceptor : handlerInterceptorList) {
            interceptorRegistry.addInterceptor(handlerInterceptor);
        }
    }
}

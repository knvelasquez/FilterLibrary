package com.filterlibrary.domain;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public interface InterceptorFactory {
    WebMvcConfigurer from(List<Object> interceptors) throws Exception;
}

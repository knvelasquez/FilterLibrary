package com.filterlibrary.adapter.factory;

import com.filterlibrary.DefaultWebMvcConfig;
import com.filterlibrary.domain.InterceptorFactory;
import com.filterlibrary.exception.FilterNotFoundException;
import com.filterlibrary.interceptor.AuthorizationBearerTokenHandlerInterceptor;
import com.filterlibrary.interceptor.JwtBasedAuthenticationHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultInterceptorFactory implements InterceptorFactory {
    private final List<HandlerInterceptor> handlerInterceptorList = new ArrayList<>();
    private final AuthorizationBearerTokenHandlerInterceptor authorizationBearerTokenHandlerInterceptor;
    private final JwtBasedAuthenticationHandlerInterceptor jwtBasedAuthenticationHandlerInterceptor;

    public DefaultInterceptorFactory(AuthorizationBearerTokenHandlerInterceptor authorizationBearerTokenHandlerInterceptor, JwtBasedAuthenticationHandlerInterceptor jwtBasedAuthenticationHandlerInterceptor) {
        this.authorizationBearerTokenHandlerInterceptor = authorizationBearerTokenHandlerInterceptor;
        this.jwtBasedAuthenticationHandlerInterceptor = jwtBasedAuthenticationHandlerInterceptor;
    }

    @Override
    public WebMvcConfigurer from(List<Object> interceptors) throws Exception {
        for (Object interceptor : interceptors) {
            handlerInterceptorList.add(toHandlerInterceptor(interceptor));
        }
        return new DefaultWebMvcConfig(handlerInterceptorList);
    }

    private HandlerInterceptor obtain(String name) throws FilterNotFoundException {
        if (name.equals("AuthorizationBearerToken")) {
            return authorizationBearerTokenHandlerInterceptor;
        }
        else if(name.equals("JwtBasedAuthenticationHandlerInterceptor"))
        {
            return jwtBasedAuthenticationHandlerInterceptor;
        }
        throw new FilterNotFoundException(name);
    }

    private HandlerInterceptor toHandlerInterceptor(Object interceptor) throws FilterNotFoundException {
        return (interceptor instanceof String)
                ? obtain(interceptor.toString()) :
                (interceptor instanceof HandlerInterceptor) ?
                        (HandlerInterceptor) interceptor : null;
    }
}

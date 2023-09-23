package com.filterlibrary.interceptor;

import com.filterlibrary.application.WhiteListService;
import com.filterlibrary.exception.AuthorizationBearerTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationBearerTokenHandlerInterceptor implements HandlerInterceptor {
    private static final String WHITE_LIST_KEY = "AuthorizationBearerTokenHandlerInterceptor";
    private static final String AUTHORIZATION_BEARER_TOKEN_HEADER = "AUTHORIZATION";
    private final WhiteListService whiteListService;

    public AuthorizationBearerTokenHandlerInterceptor(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String AuthorizationBearerToken = request.getHeader(AUTHORIZATION_BEARER_TOKEN_HEADER);
        String currentUrl = request.getRequestURI();

        if (!whiteListService.check(currentUrl, WHITE_LIST_KEY) || AuthorizationBearerToken != null) {
            return true;
        }
        throw new AuthorizationBearerTokenException();
    }
}

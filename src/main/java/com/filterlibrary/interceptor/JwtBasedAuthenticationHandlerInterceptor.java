package com.filterlibrary.interceptor;

import com.filterlibrary.application.WhiteListService;
import com.filterlibrary.exception.JwtBasedAuthenticationException;
import com.jwtlibrary.domain.JwtFactory;
import com.jwtlibrary.model.SecurityKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtBasedAuthenticationHandlerInterceptor implements HandlerInterceptor {
    private static final String WHITE_LIST_KEY = "JwtBasedAuthenticationHandlerInterceptor";
    private static final String AUTHORIZATION_BEARER_TOKEN_HEADER = "AUTHORIZATION";
    private final WhiteListService whiteListService;
    private final JwtFactory jwtFactory;

    public JwtBasedAuthenticationHandlerInterceptor(WhiteListService whiteListService, JwtFactory jwtFactory) {
        this.whiteListService = whiteListService;
        this.jwtFactory = jwtFactory;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String currentUrl = request.getRequestURI();
        if (!whiteListService.check(currentUrl, WHITE_LIST_KEY)) {
            return true;
        }
        String jwtValue = request.getHeader(AUTHORIZATION_BEARER_TOKEN_HEADER);
        SecurityKey jwtDecoded = jwtFactory.getDecoder().decode(jwtValue);
        Optional<Object> jwtDecodedResult = Optional.ofNullable(jwtDecoded);
        if (jwtDecodedResult.isPresent()) {
            SecurityKey securityKey = (SecurityKey) jwtDecodedResult.get();
            List<String> extraClaims = securityKey.getExtraClaims();
            if (extraClaims != null && !extraClaims.isEmpty()) {
                String subject = securityKey.getSubject();
                SecurityContextHolder.getContext().setAuthentication(toUserPassAuthToken(subject, extraClaims));
                return true;
            }
        }
        throw new JwtBasedAuthenticationException();
    }

    private UsernamePasswordAuthenticationToken toUserPassAuthToken(String subject, List<String> extraClaims) {
        return new UsernamePasswordAuthenticationToken(subject, null, toGrantedAuth(extraClaims));
    }

    private Collection<GrantedAuthority> toGrantedAuth(List<String> extraClaims) {
        return extraClaims.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

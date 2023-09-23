package com.filterlibrary.filter;

import com.jwtlibrary.domain.JwtFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@Order(2)
public class JwtBasedAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(JwtBasedAuthenticationFilter.class);
    private static final String AUTHORIZATION = "Authorization";
    private final JwtFactory jwtFactory;

    public JwtBasedAuthenticationFilter(JwtFactory jwtFactory) {
        this.jwtFactory = jwtFactory;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("Applying JwtBasedAuthenticationFilter to request");
        filterChain.doFilter(request, response);
    }
}

package com.filterlibrary.adapter.factory;

import com.filterlibrary.domain.JwtFilterFactory;
import com.filterlibrary.filter.DefaultFilter;
import com.filterlibrary.filter.JwtBasedAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class DefaultJwtFilterFactory implements JwtFilterFactory {
    private final JwtBasedAuthenticationFilter jwtBasedAuthenticationFilter;
    private final DefaultFilter defaultFilter;

    public DefaultJwtFilterFactory(JwtBasedAuthenticationFilter jwtBasedAuthenticationFilter, DefaultFilter defaultFilter) {
        this.jwtBasedAuthenticationFilter = jwtBasedAuthenticationFilter;
        this.defaultFilter = defaultFilter;
    }

    @Override
    public JwtBasedAuthenticationFilter getJwtBasedAuthenticationFilter() {
        return jwtBasedAuthenticationFilter;
    }

    @Override
    public DefaultFilter getDefaultFilter() {
        return defaultFilter;
    }
}

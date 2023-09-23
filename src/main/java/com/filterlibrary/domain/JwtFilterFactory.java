package com.filterlibrary.domain;

import com.filterlibrary.filter.DefaultFilter;
import com.filterlibrary.filter.JwtBasedAuthenticationFilter;

public interface JwtFilterFactory {
    JwtBasedAuthenticationFilter getJwtBasedAuthenticationFilter();
    DefaultFilter getDefaultFilter();
}

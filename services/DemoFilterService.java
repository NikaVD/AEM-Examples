package com.traineeproject.core.services;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface DemoFilterService {
    void replace(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain);
}

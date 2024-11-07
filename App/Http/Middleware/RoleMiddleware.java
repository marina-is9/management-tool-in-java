package com.example.middleware;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class RoleMiddleware extends OncePerRequestFilter {

    private final RequestMappingHandlerAdapter handlerAdapter;

    public RoleMiddleware(RequestMappingHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Get the authenticated user from the security context
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (user == null || !user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(reques

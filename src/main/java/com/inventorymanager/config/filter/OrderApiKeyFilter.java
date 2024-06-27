package com.inventorymanager.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class OrderApiKeyFilter extends OncePerRequestFilter {
    @Value("${ORDER_API_KEY}")
    private String orderApiKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String keyHeader = request.getHeader("ORDER_API_KEY");
        if(keyHeader == null || !keyHeader.equals(orderApiKey)){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Invalid key");
            return;
        }
        // Create a list of authorities
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ORDERPLACER");
        // Create an authentication token
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("orderplacer", null, Collections.singletonList(authority));
        // Set the authentication in the context
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}

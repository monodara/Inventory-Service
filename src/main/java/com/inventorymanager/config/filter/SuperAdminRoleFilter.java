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
public class SuperAdminRoleFilter extends OncePerRequestFilter {
    @Value("${SUPERADMIN_SECRET_KEY}")
    private String superAdminKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String keyHeader = request.getHeader("ADMIN_KEY");
        System.out.println("Received ADMIN_KEY: " + keyHeader);
        if(keyHeader == null || !keyHeader.equals(superAdminKey)){
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            response.getWriter().write("Invalid key");
            filterChain.doFilter(request, response);
            return;
        }
        SuperAdminAuthToken auth = new SuperAdminAuthToken();
        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(auth);
        SecurityContextHolder.setContext(newContext);
        filterChain.doFilter(request, response);
    }
}

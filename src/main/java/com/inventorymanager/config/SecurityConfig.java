package com.inventorymanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SuperAdminRoleFilter superAdminRoleFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                        req -> req
                                // super admin can NOT update supplier's info
                                .requestMatchers(HttpMethod.PATCH, "/suppliers/**").denyAll()
                                // super admin can ONLY list stocks and orders
                                .requestMatchers(HttpMethod.GET, "/stocks/**", "/orders/**").hasRole("SUPERADMIN")
                                .requestMatchers("/suppliers/**").permitAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(superAdminRoleFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

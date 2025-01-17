package com.inventorymanager.config;

import com.inventorymanager.config.filter.OrderApiKeyFilter;
import com.inventorymanager.config.filter.SuperAdminRoleFilter;
import com.inventorymanager.config.filter.SupplierAuthFilter;
import com.inventorymanager.service.authentication.SupplierDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private SupplierAuthFilter authFilter;

    @Autowired
    private SupplierDetailsService supplierDetailsService;

    @Autowired
    private SuperAdminRoleFilter superAdminRoleFilter;

    @Autowired
    private OrderApiKeyFilter orderApiKeyFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/api/v1/suppliers/login").permitAll()

                                // "Order placer" can create/delete/cancle order
                                // For later integration with fullstack project
                                .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasAnyAuthority("ORDERPLACER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAnyAuthority("ORDERPLACER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/cancel/**").hasAnyAuthority("ORDERPLACER")

                                .requestMatchers("/api/v1/reports/**").hasAnyAuthority("SUPERADMIN", "SUPPLIER")
                                // super admin can create/update/delete supplier and stock
                                .requestMatchers("/api/v1/suppliers/**","/api/v1/stocks/**").hasAnyAuthority("SUPERADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders/**").hasAnyAuthority("SUPERADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/**").hasAnyAuthority("SUPERADMIN")

                                .anyRequest().permitAll()
                )
                .userDetailsService(supplierDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(superAdminRoleFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(orderApiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

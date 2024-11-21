package com.ctoutweb.lalamiam.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(request->request
            .requestMatchers("/admin/professional/documents").permitAll()
            .anyRequest().authenticated()
    );

    return http.build();
  }
}

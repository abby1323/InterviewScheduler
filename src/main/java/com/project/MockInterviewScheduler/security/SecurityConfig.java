package com.project.MockInterviewScheduler.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain setFilter(HttpSecurity http){
        return http
                .csrf(csrf-> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth-> auth
                        // public URL
                                .requestMatchers("auth/**").permitAll()
                        // student only
                        .requestMatchers("/api/v1/students/**").hasAuthority("STUDENT")
                        // interviewers only
                        .requestMatchers("/api/v1/interviewers/**").hasAuthority("INTERVIEWER")
                        // placement-admin only
                        .requestMatchers("/placement-admin/").hasAuthority("PLACEMENT_ADMIN")
                                .anyRequest()
                                .authenticated()
                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}

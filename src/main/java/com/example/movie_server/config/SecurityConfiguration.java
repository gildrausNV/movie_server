package com.example.movie_server.config;

import com.example.movie_server.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/movies**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/movies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actors**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/movies**").hasAnyAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.POST, "/actors**").hasAnyAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.POST, "/reviews**").hasAnyAuthority(Role.USER.getAuthority())
                        .requestMatchers(HttpMethod.DELETE, "/reviews**").hasAnyAuthority(Role.USER.getAuthority())
                        .requestMatchers(HttpMethod.POST, "/reviews**").hasAnyAuthority(Role.ADMIN.getAuthority())

                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

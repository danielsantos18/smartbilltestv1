package com.v1.smartbill.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/smartphone/save").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "api/smartphone/update").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/smartphone/delete/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "api/smartphone/list").hasAnyAuthority("ADMIN" , "USER")
                        .requestMatchers(HttpMethod.GET, "api/smartphone/listId/**").hasAnyAuthority("ADMIN" , "USER")
                        .anyRequest().authenticated());

        return http.build();
    }
}

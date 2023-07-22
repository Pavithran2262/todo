package com.todoapp.backend_application.Configuration;

import com.todoapp.backend_application.ExceptionHandling.GlobalException;
import com.todoapp.backend_application.Filter.JwtAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

//This SecurityConfiguration class is used for the configure the authentication manager , authentication provider ,
// password encoder and permitable endpoint
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JwtAuthFilter authFilter;

    Logger log = LoggerFactory.getLogger(GlobalException.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).
                authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests.requestMatchers("/user/register", "/user/login", "/user/welcome", "/admin/register", "/admin/login").permitAll()
//                            .requestMatchers("/admin/**").hasAnyRole("ADMIN")
//                            .requestMatchers("/user/**").hasRole("USER")
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        log.debug("..........from security config - authentication provider ...........................");
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        log.debug("..........from security config - authentication manager ...........................");
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }
}









































//    @Bean
//
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        log.debug("inside filter chain method");
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/auth/**").permitAll();
//                    auth.requestMatchers("/admin/**").hasRole(Role.AuthorityLevel.ADMIN.name());
//                    auth.requestMatchers("/customer/**").hasAnyRole(Role.AuthorityLevel.CUSTOMER.name(), Role.AuthorityLevel.ADMIN.name());
//                    auth.anyRequest().authenticated();
//                });
//
//
//        http.oauth2ResourceServer(oauth2 -> {
//            oauth2.jwt(jwt -> {
//                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
//            });
//        });
//        http.sessionManagement(
//                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        return http.build();
//    }

package com.booking.config;

import com.booking.services.UserService;
import com.booking.encoder.Sha512Encoder;
import com.booking.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfiguration implements WebMvcConfigurer {
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                requests -> requests
                        .requestMatchers("/private/**").authenticated()
                        .requestMatchers("/register/**", "/confirm/hash/**", "/login/**").anonymous()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
        )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .exceptionHandling(config -> {
                config.accessDeniedHandler(accessDeniedHandler());
                config.authenticationEntryPoint(noUserEntryPoint());
            });

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/js/**")
            .addResourceLocations("classpath:/public/js/");

        registry
            .addResourceHandler("/css/**")
            .addResourceLocations("classpath:/public/css/");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Sha512Encoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomHttp403ForbiddenEntryPoint noUserEntryPoint(){
        return new CustomHttp403ForbiddenEntryPoint();
    }
}

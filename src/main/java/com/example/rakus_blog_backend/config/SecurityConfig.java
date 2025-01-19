package com.example.rakus_blog_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(this.corsConfigurationSource()));
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/users/*").authenticated()
                .anyRequest().permitAll());
        http.addFilterBefore(new AuthorizeFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider(UserDetailsService LoginUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(LoginUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);

        corsConfiguration.addExposedHeader("X-AUTH-TOKEN");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();

        corsSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsSource;
    }

}

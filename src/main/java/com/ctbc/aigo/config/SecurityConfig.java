package com.ctbc.aigo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.access.IpAddressAuthorizationManager.hasIpAddress;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // 架在VM上面可以存取的Local IP
    @Value(value = "${accessible.ips}")
    String accessibleIps;

    // 是否要開啟IP Filter功能
    @Value(value = "${accessible.enable}")
    boolean accessibleEnable;

    // default whitelist to actuators and swaggers
    private static final String[] AUTH_WHITELIST = {
            "/v3/aigo-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/actuator/**"
    };

    /**
     * CORS filter解決CORS問題
     *
     * @return CorsFilter
     */
    @Bean
    @Order(0)
    public CorsFilter corsFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(corsConfigurationSource);
    }

    /**
     * 過濾ip filter chain
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    @Order(1)
    SecurityFilterChain filterIps(HttpSecurity http) throws Exception {
        if (accessibleEnable) {
            http.authorizeHttpRequests(auth ->
                            auth.requestMatchers("/api/**")
                                    .access(hasIpAddress(accessibleIps))
                    ).cors(withDefaults())
                    .csrf(csrf -> csrf.disable());
        } else {
            http.authorizeHttpRequests(auth ->
                            auth.requestMatchers("/**").permitAll()
                    ).cors(withDefaults())
                    .csrf(csrf -> csrf.disable());
        }

        return http.build();
    }

}
package com.lab.microservices.gateway.config;

import com.lab.microservices.gateway.security.CustomUserDetailsService;
import com.lab.microservices.gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager нужен для ручной аутентификации в AuthController.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * DaoAuthenticationProvider настраивает UserDetailsService и PasswordEncoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Основная конфигурация HTTP Security.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF и CORS (REST API без браузерных форм)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                // Вставляем фильтр JWT перед фильтром аутентификации по форме
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // Доступ без аутентификации
                        .requestMatchers("/api/auth/**").permitAll()
                        // Доступ к GET /api/cats/** – ROLE_USER или ROLE_ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/cats/**").hasAnyRole("USER", "ADMIN")
                        // POST /api/cats – ROLE_USER или ROLE_ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/cats/**").hasAnyRole("USER", "ADMIN")
                        // PUT /api/cats – ROLE_ADMIN или Владелец кота (@securityService.isOwnerOfCat)
                        .requestMatchers(HttpMethod.PUT, "/api/cats/**").hasAnyRole("USER","ADMIN")
                        // DELETE /api/cats – ROLE_ADMIN или Владелец кота
                        .requestMatchers(HttpMethod.DELETE, "/api/cats/**").hasAnyRole("USER","ADMIN")
                        // GET /api/owners/{id} – ROLE_USER или ROLE_ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/owners/**").hasAnyRole("USER", "ADMIN")
                        // Остальные запросы – только аутентифицированные
                        .anyRequest().authenticated()
                )
                // Конфигурация formLogin (логин через /api/auth/login)
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler((req, res, auth) ->
                                res.setStatus(HttpStatus.OK.value()))
                        .failureHandler((req, res, ex) ->
                                res.sendError(HttpStatus.UNAUTHORIZED.value()))
                        .permitAll()
                )
                // Конфигурация logout (через /api/auth/logout)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((req, res, auth) ->
                                res.setStatus(HttpStatus.OK.value()))
                        .permitAll()
                );

        // Устанавливаем наш DaoAuthenticationProvider
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}

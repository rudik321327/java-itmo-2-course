package com.app_with_database.security;

import com.app_with_database.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр, который:
 * 1) читает JWT из заголовка Authorization (Bearer …)
 * 2) проверяет валидность через jwtProvider.validateToken(...)
 * 3) если валидно — извлекает username, грузит UserDetails и помещает
 *    Authentication в SecurityContext.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Получаем заголовок
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            // 2. Извлекаем токен
            String token = header.substring(7);

            // 3. Проверяем токен (здесь правильный метод validateToken)
            if (jwtProvider.validateToken(token)) {
                // 4. Берём username и грузим UserDetails
                String username = jwtProvider.getUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. Создаём Authentication и кладём в контекст
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
            }
        }

        // Продолжаем цепочку фильтров
        filterChain.doFilter(request, response);
    }
}

package ru.spring.entity_manager.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.spring.entity_manager.security.CustomUserDetailsService;
import ru.spring.entity_manager.user.UserService;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);

    private final JwtTokenManager jwtTokenManager;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenFilter(JwtTokenManager jwtTokenManager, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenManager = jwtTokenManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.replace("Bearer ", "");

        String loginFromToken;
        try {
            loginFromToken = jwtTokenManager.getLoginFromToken(token);
        } catch (Exception e) {
            log.error("Error while getting login from token", e);
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails user = customUserDetailsService.loadUserByUsername(loginFromToken);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken
                        (
                                user,
                                null,
                                user.getAuthorities()
                        );
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

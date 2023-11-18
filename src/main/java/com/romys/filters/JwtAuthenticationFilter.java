package com.romys.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.romys.services.JwtService;
import com.romys.services.implement.UserServiceImplement;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImplement userServiceImplement;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        String content = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username, token;
        if (content != null && content.startsWith("Bearer")) {
            token = content.substring(7);
            username = this.jwtService.extractUsername(token);

            if (username != null) {
                if (context.getAuthentication() == null) {
                    UserDetails user = this.userServiceImplement.loadUserByUsername(username);

                    if (jwtService.isValid(token, user)) {
                        System.out.println("\033[1;32mValid\033[1;38;5;214m: \033[1;38;5;111m"
                                + user.getAuthorities() + "\033[0m\n");
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                user, null, user.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        context.setAuthentication(authenticationToken);

                        // response.setHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s",
                        // token));
                    } else {
                        System.out.println("\033[1;31mInvalid\033[1;38;5;214m: \033[1;38;5;111m"
                                + user.getAuthorities() + "\033[0m\n");
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}

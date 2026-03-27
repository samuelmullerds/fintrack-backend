package com.fintrack.fintrack_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Collections;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter{

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{    
        
        if (request.getRequestURI().startsWith("/auth")) {
        filterChain.doFilter(request, response);
        return;
        }
        
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){

            String token = authHeader.replace("Bearer ", "");

    try{

        Long userId = Long.parseLong(jwtService.validateToken(token));

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userId,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    catch(Exception e){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }
    }
      filterChain.doFilter(request, response);
    
}
}
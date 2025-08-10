package com.exercise.project.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exercise.project.security.jwt.utils.JwtUtilsInterface;
import com.exercise.project.security.user.AuthUserDetails;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {

    @Value("${project.jwt.bearer.prefix}")
    private String BEARER_PREFIX;

    @Autowired
    private JwtUtilsInterface jwtUtils;

    @Override
    protected void doFilterInternal(
        @SuppressWarnings("null") HttpServletRequest request,
        @SuppressWarnings("null") HttpServletResponse response,
        @SuppressWarnings("null") FilterChain filterChain)
        throws ServletException, IOException, UsernameNotFoundException {
        try {
            String jwt = this.parseJwt(request);
            if (StringUtils.hasText(jwt) && this.jwtUtils.validateToken(jwt)) {
                AuthUserDetails userDetails = this.jwtUtils.buildUserDetailsFromToken(jwt);
                UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(
                    userDetails, null,
                    userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage() + " : Invalid or expired token, you may login and try again");
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(this.BEARER_PREFIX)) {
            return headerAuth.substring(this.BEARER_PREFIX.length());
        }
        return null;
    }

}

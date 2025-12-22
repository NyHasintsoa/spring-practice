package com.exercise.project.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exercise.project.security.jwt.parser.JwtTokenParserInterface;
import com.exercise.project.security.jwt.revoke.JwtRevokeTokenInterface;
import com.exercise.project.security.jwt.utils.JwtUtilsInterface;
import com.exercise.project.security.user.AuthUserDetails;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class AuthRequestFilter extends OncePerRequestFilter {

    @Value("${project.jwt.bearer.prefix}")
    private String BEARER_PREFIX;

    @Autowired
    private JwtUtilsInterface jwtUtils;

    @Autowired
    private JwtRevokeTokenInterface jwtRevokeToken;

    @Autowired
    private JwtTokenParserInterface jwtTokenParser;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException, UsernameNotFoundException {
        try {
            String jwt = jwtTokenParser.parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
                if (jwtRevokeToken.isTokenRevoked(jwtUtils.extractTokenId(jwt))) {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    final Map<String, Object> body = new HashMap<>();
                    body.put("error", "Unauthorized");
                    body.put("message", "Invalid or expired token, you may login and try again");
                    body.put("path", request.getServletPath());
                    final ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(response.getOutputStream(), body);

                    return;
                }
                AuthUserDetails userDetails = jwtUtils.buildUserDetailsFromToken(jwt);
                UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(
                    userDetails, null,
                    userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            final Map<String, Object> body = new HashMap<>();
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("error", "Unauthorized");
            body.put("message", e.getMessage() + " : Invalid or expired token, you may login and try again");
            body.put("path", request.getServletPath());
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);

            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

}

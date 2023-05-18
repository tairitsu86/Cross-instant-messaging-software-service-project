package com.cimss.project.security;

import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.UserId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component("JwtAuthenticationFilter")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ADMIN_TOKEN = System.getenv("CIMSS_ADMIN_TOKEN");
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DatabaseService databaseService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtilities.getToken(request);
        if(ADMIN_TOKEN!=null&&ADMIN_TOKEN.equals(token))
            log.info("authenticated user with admin token");
        else if (token != null && jwtUtilities.validateToken(token)) {
            String jsonUserId = jwtUtilities.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(jsonUserId);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                log.info(String.format("authenticated user with MemberId :{%s}",jsonUserId));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
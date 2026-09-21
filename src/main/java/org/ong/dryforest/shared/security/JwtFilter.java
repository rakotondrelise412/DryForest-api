package org.ong.dryforest.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.ong.dryforest.modules.identity.service.UserService;
import org.ong.dryforest.shared.constants.RoutesConstants;
import org.ong.dryforest.shared.constants.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@NullMarked
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var path = request.getServletPath();

        if (isPublicRoute(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        var header = request.getHeader(SecurityConstants.AUTH_HEADER);

        if (header == null || !header.startsWith(SecurityConstants.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = header.substring(SecurityConstants.BEARER_PREFIX.length());

        if (!jwtService.isValid(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        var username = jwtService.extractUsername(token);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = userService.loadUserByUsername(username);

            var auth = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            );

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicRoute(String path) {
        return path.startsWith(RoutesConstants.PUBLIC[0].substring(0, 6));
    }
}
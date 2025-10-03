package br.com.gtechnologia.savewithme.adapters.in.web.rest.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import br.com.gtechnologia.savewithme.application.ports.out.provider.TokenProvider;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenProvider tokens;

    public JwtAuthFilter(TokenProvider tokens) { this.tokens = tokens; }

    @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        var header = Optional.ofNullable(req.getHeader("Authorization")).orElse("");
        if (header.startsWith("Bearer ")) {
            try {
                var decoded = tokens.verify(header.substring(7));
                req.setAttribute("userId", decoded.user().getId().value());
                req.setAttribute("email", decoded.user().getEmail().value());
            } catch (JwtException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token"); return;
            }
        }
        chain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.endsWith("auth/login") || path.endsWith("auth/register");
    }
}

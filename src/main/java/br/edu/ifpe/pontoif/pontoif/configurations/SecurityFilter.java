package br.edu.ifpe.pontoif.pontoif.configurations;

import br.edu.ifpe.pontoif.pontoif.exception.NotFoundException;
import br.edu.ifpe.pontoif.pontoif.repository.UserRepository;
import br.edu.ifpe.pontoif.pontoif.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null && !token.isBlank()) {
            var email = tokenService.validateToken(token);
            if (email != null) {
                var user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundException("User Not Found"));
                var authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toAuthorize()));
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}

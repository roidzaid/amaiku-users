package com.amaiku.users.security;

import com.amaiku.users.controllers.UsuarioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final JwtService jwtService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        logger.info("request: " + request.getRequestURI());
        logger.info("method: " + request.getMethod());

        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean esPublico = (path.startsWith("/auth"));

        // 👇 Evitá pasar por el filtro JWT si es POST /usuarios
        if (esPublico) {
            logger.info("Saltando JWT para /auth");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = request.getHeader("authorization");

            if (jwtService.isBearer(authHeader)) {

                List<GrantedAuthority> authorities = jwtService.roles(authHeader).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(jwtService.user(authHeader), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else {
                // ⚠️ No es Bearer → NO autenticado → lanzamos excepción para 401
                throw new Exception("No Authorization Bearer token");
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }
    }

}

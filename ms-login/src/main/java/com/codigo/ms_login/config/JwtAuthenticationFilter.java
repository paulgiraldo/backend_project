package com.codigo.ms_login.config;


import com.codigo.ms_login.service.JWTService;
import com.codigo.ms_login.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;            // Para Manipular los Token
    private final UsuarioService usuarioService;    // Para cargar los datos a SpringSecurity desde mi Usuario

    // HttpServletRequest, tiene el header y body de la petición
    // HttpServletResponse, tiene un header y body de la respuesta
    // FilterChain, es nuestro filtro, se define si pasa o no pasa la petición
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Se tiene dos escenarios: cuando se tiene un token y cuando no se tiene un token
        // del request, capturamos de la cabecera el Authorization (Alli es donde esta el token, si tuviera)
        final String autHeader = request.getHeader("Authorization");
        final String jwt;
        final String userCod;

        // StringUtils será deprecado, utilizar la linea: if (autHeader != null && autHeader.startsWith("Bearer ")) {}
        // Si no se envió un token, entonces el filter pasa la solicitud y respuesta sin hacer otra validacion
        if (StringUtils.isEmpty(autHeader) || !StringUtils.startsWithIgnoreCase(autHeader,"Bearer ")){
           filterChain.doFilter(request, response);
           return;
        }

        // si se envió un token, eliminar el inicio "Bearer "
        jwt = autHeader.substring(7);
        userCod = jwtService.extracUserName(jwt);

        // Verificando que tengamos un usuario valido y que no exista un objeto de autenticacion en nuestro CONTEXTO DE SEGURIDAD
        if(Objects.nonNull(userCod) && SecurityContextHolder.getContext().getAuthentication() == null){
            // si esta ok, procedemos a cargar en el UserDetails a nuestro usuario
            UserDetails userDetails = usuarioService.userDetailService().loadUserByUsername(userCod);

            System.out.println(userDetails);
            // Validamos el Token y si es valido creamos un CONTEXTO DE SEGURIDAD vacio, para nosotros ingresar informacion
            // se setea un: Authentication, después un SecurityContext y por ultimo SecurityContextHolder
            if (jwtService.validateToken(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                // creamos un Authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Seteamos el Authenticatio, para que regrese a realizar la solicitud al endpoint
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Seteamos el securitycontext creado con los datos cargado en el authenticationToken
                securityContext.setAuthentication(authenticationToken);
                // también seteamos el SecurityContextHolder
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}

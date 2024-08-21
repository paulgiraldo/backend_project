package com.codigo.ms_login.service.imp;

import com.codigo.ms_login.client.ClientReniec;
import com.codigo.ms_login.dto.ValidaTokenDto;
import com.codigo.ms_login.entities.UsuarioEntity;
import com.codigo.ms_login.repository.UsuarioRepository;
import com.codigo.ms_login.service.JWTService;
import com.codigo.ms_login.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JWTService jwtService;

    @Override
    public UserDetailsService userDetailService() {
        // me retorna el objeto que es una interfaz, y a su vez me da el espacio para codificar la implementación
        // directamente, sin necesidad de crear una clase e implementar toda la interfaz UserDetailsService
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return usuarioRepository.findByUsuarioCod(username).orElseThrow( () ->
                        new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }

    @Override
    public List<UsuarioEntity> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioEntity getUsuarioXId(String usuarioCod) {
        return usuarioRepository.findByUsuarioCod(usuarioCod).orElse(null);
    }

    @Override
    public ValidaTokenDto validarToken(String token, String endPoint) {
        final String jwt;
        final String userCod;

        ValidaTokenDto validaTokenDto = new ValidaTokenDto();

        if (StringUtils.isEmpty(token) || !StringUtils.startsWithIgnoreCase(token,"Bearer ")){
            validaTokenDto.setRespuesta(false);
            validaTokenDto.setMotivo("Token invalido");
            return validaTokenDto;
        }
        // si se envió un token, eliminar el inicio "Bearer "
        jwt = token.substring(7);
        userCod = jwtService.extracUserName(jwt);

        // Verificando que tengamos un usuario valido
        if(Objects.nonNull(userCod) ) {
            // si esta ok, procedemos a cargar en el UserDetails a nuestro usuario
            if (!usuarioRepository.existsByUsuarioCod(userCod)) {
                validaTokenDto.setRespuesta(false);
                validaTokenDto.setMotivo("Usuario no encontrado");
                return validaTokenDto;
            }
        }
        // Verificando que el token no haya expirado
        if (jwtService.isTokenExpired2(jwt)){
            validaTokenDto.setRespuesta(false);
            validaTokenDto.setMotivo("El Token a Expirado");
            return validaTokenDto;
        }
        validaTokenDto.setRespuesta(true);
        validaTokenDto.setMotivo("Validacion OK");

        return validaTokenDto;
    }

}

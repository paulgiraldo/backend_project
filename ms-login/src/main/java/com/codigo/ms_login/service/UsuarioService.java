package com.codigo.ms_login.service;

import com.codigo.ms_login.dto.ValidaTokenDto;
import com.codigo.ms_login.entities.UsuarioEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService {
    UserDetailsService userDetailService();

    List<UsuarioEntity> getUsuarios();
    UsuarioEntity getUsuarioXId(String usuarioCod);

    ValidaTokenDto validarToken(String token, String endPoint);
}

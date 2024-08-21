package com.codigo.ms_login.response;

import com.codigo.ms_login.entities.RolEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private String usuarioNom;
    private Set<RolEntity> usuarioRol;
}

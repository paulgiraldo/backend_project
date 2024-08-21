package com.codigo.ms_login.request;

import com.codigo.ms_login.entities.RolEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class UsuarioRequest {
    private String usuarioCod;
    private String usuarioDni;
    private String usuarioEstrabajador;
    private String usuarioClave;
    private String gestionXArea;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date cuentaFechatermino;
    private String cuentaBloqueada;
    private String credencialExpirado;
    private String eliminado;
    private String eliminadoMotivo;
    private Set<RolEntity> roles;
    private Set<UsuarioAreaRequest> areas;
}

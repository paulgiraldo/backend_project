package com.codigo.ms_login.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="usuarios")
public class UsuarioEntity implements UserDetails {
    @Id
    @Column(name="usuario_cod")
    private String usuarioCod;
    @Column(name="usuario_dni")
    private String usuarioDni;
    @Column(name="usuario_estrabajador")
    private String usuarioEstrabajador;
    @Column(name="usuario_nom")
    private String usuarioNom;
    @Column(name="usuario_clave")
    private String usuarioClave;
    @Column(name="gestion_xarea")
    private String gestionXArea;
    @Column(name="cuenta_fechatermino")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date cuentaFechatermino;
    @Column(name="cuenta_bloqueada")
    private String cuentaBloqueada;
    @Column(name="credencial_expirado")
    private String credencialExpirado;
    @Column(name="eliminado")
    private String eliminado;
    @Column(name="eliminado_motivo")
    private String eliminadoMotivo;
    @Column(name="crea_usuario_cod")
    private String creaUsuarioCod;
    @Column(name="crea_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date creaFecha;
    @Column(name="modifica_usuario_cod")
    private String modificaUsuarioCod;
    @Column(name="modifica_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date modificaFecha;
    @Column(name="eliminado_usuario_cod")
    private String eliminadoUsuarioCod;
    @Column(name="eliminado_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date eliminadoFecha;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
                joinColumns = @JoinColumn(name="usuario_cod"),
                inverseJoinColumns = @JoinColumn(name="rol_cod"))
    private Set<RolEntity> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_area",
            joinColumns = @JoinColumn(name="usuario_cod"),
            inverseJoinColumns = @JoinColumn(name="area_cod"))
    private Set<AreaEntity> areas = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolCod()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return usuarioClave;
    }

    @Override
    public String getUsername() {
        return usuarioCod;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (ObjectUtils.isEmpty(cuentaFechatermino)){
            return true;
        } else {
            return !cuentaFechatermino.before(new Date());
        }
    }

    @Override
    public boolean isAccountNonLocked() {
        return cuentaBloqueada.equals("N");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credencialExpirado.equals("N");
    }

    @Override
    public boolean isEnabled() {
        return eliminado.equals("N");
    }
}

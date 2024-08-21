package com.codigo.ms_login.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "areas")
@Getter
@Setter
public class AreaEntity {
    @Id
    @Column(name="area_cod")
    private String areaCod;
    @Column(name="area_nom")
    private String areaNom;
    @Column(name="eliminado")
    private String eliminado;
    @Column(name="eliminado_motivo")
    private String eliminadoMotivo;
    @Column(name="crea_usuario_cod")
    private String creaUsuarioCod;
    @Column(name="crea_fecha")
    private Date creaFecha;
    @Column(name="modifica_usuario_cod")
    private String modificaUsuarioCod;
    @Column(name="modifica_fecha")
    private Date modificaFecha;
    @Column(name="eliminado_usuario_cod")
    private String eliminadoUsuarioCod;
    @Column(name="eliminado_fecha")
    private Date eliminadoFecha;
}

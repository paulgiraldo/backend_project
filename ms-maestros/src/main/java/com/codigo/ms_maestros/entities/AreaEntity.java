package com.codigo.ms_maestros.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="areas")
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
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private java.util.Date creaFecha;
    @Column(name="modifica_usuario_cod")
    private String modificaUsuarioCod;
    @Column(name="modifica_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private java.util.Date modificaFecha;
    @Column(name="eliminado_usuario_cod")
    private String eliminadoUsuarioCod;
    @Column(name="eliminado_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date eliminadoFecha;
}

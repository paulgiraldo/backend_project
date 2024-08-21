package com.codigo.ms_vacaciones.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@Entity
@Table(name="vacaciones_solicitud")
public class VacacionesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vacacion_id")
    private Long Id;

    @Column(name="trab_cod")
    private String trabCod;

    @Column(name="trab_nom")
    private String trabNom;

    @Column(name="saldo_dias")
    private Integer saldoDias;
    @Column(name="fecha_inicial")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaInicial;
    @Column(name="nro_dias")
    private Integer nroDias;
    @Column(name="fecha_final")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaFinal;
    @Column(name="motivo")
    private String motivo;
    @Column(name="obs_record")
    private String obsRecord;
    @Column(name="area_cod")
    private String areaCod;
    @Column(name="aprobacion01")
    private String aprobacion01;
    @Column(name="aprobacion01_usuario_cod")
    private String aprobacion01UsuarioCod;
    @Column(name="aprobacion01_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date aprobacion01Fecha;
    @Column(name="aprobacion02")
    private String aprobacion02;
    @Column(name="aprobacion02_usuario_cod")
    private String aprobacion02UsuarioCod;
    @Column(name="aprobacion02_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date aprobacion02Fecha;
    @Column(name="control_revision")
    private String controlRevision;
    @Column(name="control_revision_obs")
    private String controlRevisionObs;
    @Column(name="control_usuario_cod")
    private String controlUsuarioCod;
    @Column(name="control_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date controlFecha;
    @Column(name="eliminado")
    private String eliminado;
    @Column(name="eliminado_motivo")
    private String eliminadoMotivo;
    @Column(name="crea_usuario_cod")
    private String creaUsuarioCod;
    @Column(name="crea_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date creaFecha;
    @Column(name="eliminado_usuario_cod")
    private String eliminadoUsuarioCod;
    @Column(name="eliminado_fecha")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
    private Date eliminadoFecha;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trab_cod",insertable=false, updatable=false)
    private TrabajadorEntity trabajadorEntity;


}

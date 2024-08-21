package com.codigo.ms_vacaciones.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name="trabajadores")
public class TrabajadorEntity {
    @Id
    @Column(name="trab_cod")
    private String trabCod;
    @Column(name="docident_tipo")
    private String docidentTipo;
    @Column(name="docident_nro")
    private String docidentNro;
    @Column(name="trab_nom")
    private String trabNom;
    @Column(name="trab_estado")
    private String trabEstado;
    @Column(name="trab_cargo")
    private String trabCargo;
    @Column(name="trab_condicion")
    private String trabCondicion;
    @Column(name="fecha_nacimiento")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaNacimiento;
    @Column(name="fecha_ingreso")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaIngreso;
    @Column(name="fecha_termino")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaTermino;
    @Column(name="fecha_cese")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaCese;
    @Column(name="area_cod")
    private String areaCod;
    @Column(name="fotografia")
    private String fotografia;
    @Column(name="puerta_acceso_id")
    private String puertaAccesoId;
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

 //   @OneToMany(mappedBy = "trabajadorEntity", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
 //   private Set<VacacionesEntity> vacacionesEntities;

}

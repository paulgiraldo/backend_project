package com.codigo.ms_maestros.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TrabajadorRequest {
    private String trabCod;
    private String docidentTipo;
    private String docidentNro;
    private String trabNom;
    private String trabEstado;
    private String trabCargo;
    private String trabCondicion;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaNacimiento;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaIngreso;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaTermino;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaCese;
    private String areaCod;
    private String fotografia;
    private String puertaAccesoId;
}

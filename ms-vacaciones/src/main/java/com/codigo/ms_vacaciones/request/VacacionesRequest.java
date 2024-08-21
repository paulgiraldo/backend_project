package com.codigo.ms_vacaciones.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class VacacionesRequest {
    private String trabCod;
    private String trabNom;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaInicial;
    private Integer nroDias;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date fechaFinal;
    private String motivo;
}

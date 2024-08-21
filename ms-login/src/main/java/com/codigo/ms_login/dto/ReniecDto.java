package com.codigo.ms_login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReniecDto {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}

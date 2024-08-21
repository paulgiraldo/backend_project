package com.codigo.ms_vacaciones.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidaTokenDto {
    private boolean respuesta;
    private String motivo;
}

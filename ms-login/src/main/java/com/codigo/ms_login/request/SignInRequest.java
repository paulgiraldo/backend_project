package com.codigo.ms_login.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String usuarioCod;
    private String password;
}

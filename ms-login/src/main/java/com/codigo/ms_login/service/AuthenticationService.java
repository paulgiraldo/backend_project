package com.codigo.ms_login.service;

import com.codigo.ms_login.request.UsuarioRequest;
import com.codigo.ms_login.request.SignInRequest;
import com.codigo.ms_login.response.AuthenticationResponse;
import com.codigo.ms_login.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<BaseResponse> signUpUser(UsuarioRequest singUpRequest);

    AuthenticationResponse signin(SignInRequest singInRequest);

}

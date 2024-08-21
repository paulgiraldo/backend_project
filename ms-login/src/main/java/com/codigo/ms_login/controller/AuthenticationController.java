package com.codigo.ms_login.controller;

import com.codigo.ms_login.dto.ValidaTokenDto;
import com.codigo.ms_login.entities.UsuarioEntity;
import com.codigo.ms_login.request.SignInRequest;
import com.codigo.ms_login.request.UsuarioRequest;
import com.codigo.ms_login.response.AuthenticationResponse;
import com.codigo.ms_login.response.BaseResponse;
import com.codigo.ms_login.service.AuthenticationService;
import com.codigo.ms_login.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:5173")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UsuarioService usuarioService;

    @PostMapping("/usuario/crea")
    public ResponseEntity<BaseResponse> usuarioCrea(@RequestBody UsuarioRequest usuarioRequest) {
        return authenticationService.signUpUser(usuarioRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest singInRequest) {
        return ResponseEntity.ok(authenticationService.signin(singInRequest));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioXId(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.getUsuarioXId(id));
    }

    @GetMapping("/validatoken")
    public ValidaTokenDto validatoken(@RequestParam String token, @RequestParam String endPoint) {
        return usuarioService.validarToken(token, endPoint);
    }

}

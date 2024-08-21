package com.codigo.ms_maestros.controller;

import com.codigo.ms_maestros.client.ClientValidaToken;
import com.codigo.ms_maestros.constants.Constant;
import com.codigo.ms_maestros.entities.TrabajadorEntity;
import com.codigo.ms_maestros.request.TrabajadorRequest;
import com.codigo.ms_maestros.response.BaseResponse;
import com.codigo.ms_maestros.response.TrabDto;
import com.codigo.ms_maestros.response.ValidaTokenDto;
import com.codigo.ms_maestros.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TrabajadorController {
    private final TrabajadorService trabajadorService;
    private final ClientValidaToken validaToken;
    private final String endPoint=" ";
    @PostMapping("/trabajador/crea")
    public ResponseEntity<BaseResponse<TrabajadorEntity>> crea(@RequestHeader("Authorization") String tokenExt, @RequestBody TrabajadorRequest request) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return trabajadorService.crear(request);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }

    }
    @PostMapping("/trabajador/actualiza/{codigo}")
    public ResponseEntity<BaseResponse<TrabajadorEntity>> actualiza(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo, @RequestBody TrabajadorRequest request) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return trabajadorService.actualizar(codigo,request);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }

    }
    @PostMapping("/trabajador/elimina")
    public ResponseEntity<BaseResponse<TrabajadorEntity>> elimina(@RequestHeader("Authorization") String tokenExt, @RequestParam String codigo, @RequestParam String motivo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return trabajadorService.eliminar(codigo,motivo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/trabajador/buscaxid/{codigo}")
    public ResponseEntity<BaseResponse<TrabajadorEntity>> buscaxid(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return trabajadorService.buscarXId(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/trabajador/buscaxarea/{lista}")
    public ResponseEntity<BaseResponse<List<TrabajadorEntity>>> buscaxarea(@RequestHeader("Authorization") String tokenExt, @PathVariable List<String> lista) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return trabajadorService.buscarXArea(lista);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/trabajador/buscatodos")
    public ResponseEntity<BaseResponse<List<TrabajadorEntity>>> buscatodos(@RequestHeader("Authorization") String tokenExt) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return trabajadorService.buscarTodos();
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/trabajador/buscaxiddto/{codigo}")
    public TrabDto buscarXIdDTO(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo) {
        return trabajadorService.buscarXIdDTO(codigo);
    }

    private ValidaTokenDto getExecValidaToken(String token, String endPoint){
        return validaToken.getInfoValidaToken(token, endPoint);
    }

}

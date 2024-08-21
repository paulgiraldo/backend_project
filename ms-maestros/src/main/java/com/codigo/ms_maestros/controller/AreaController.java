package com.codigo.ms_maestros.controller;

import com.codigo.ms_maestros.client.ClientValidaToken;
import com.codigo.ms_maestros.constants.Constant;
import com.codigo.ms_maestros.entities.AreaEntity;
import com.codigo.ms_maestros.request.AreaRequest;
import com.codigo.ms_maestros.response.BaseResponse;
import com.codigo.ms_maestros.response.ValidaTokenDto;
import com.codigo.ms_maestros.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;
    private final ClientValidaToken validaToken;
    private final String endPoint=" ";

    @PostMapping("/area/crea")
    public ResponseEntity<BaseResponse<AreaEntity>> crea(@RequestHeader("Authorization") String tokenExt, @RequestBody AreaRequest request) {
        return areaService.crear(request);
    }
    @PostMapping("/area/actualiza/{codigo}")
    public ResponseEntity<BaseResponse<AreaEntity>> actualiza(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo, @RequestBody AreaRequest request) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return areaService.actualizar(codigo,request);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @PostMapping("/area/elimina")
    public ResponseEntity<BaseResponse<AreaEntity>> elimina(@RequestHeader("Authorization") String tokenExt, @RequestParam String codigo, @RequestParam String motivo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return areaService.eliminar(codigo,motivo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/area/buscaxid/{codigo}")
    public ResponseEntity<BaseResponse<AreaEntity>> buscaxid(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return areaService.buscarXId(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/area/buscatodos")
    public ResponseEntity<BaseResponse<List<AreaEntity>>> buscatodos(@RequestHeader("Authorization") String tokenExt) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);

        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return areaService.buscarTodos();
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    private ValidaTokenDto getExecValidaToken(String token, String endPoint){
        return validaToken.getInfoValidaToken(token, endPoint);
    }

}

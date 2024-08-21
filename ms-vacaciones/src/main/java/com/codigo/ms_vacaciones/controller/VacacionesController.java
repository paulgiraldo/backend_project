package com.codigo.ms_vacaciones.controller;

import com.codigo.ms_vacaciones.client.ClientValidaToken;
import com.codigo.ms_vacaciones.constant.Constant;
import com.codigo.ms_vacaciones.entities.VacacionesEntity;
import com.codigo.ms_vacaciones.request.VacacionesRequest;
import com.codigo.ms_vacaciones.response.BaseResponse;
import com.codigo.ms_vacaciones.response.ValidaTokenDto;
import com.codigo.ms_vacaciones.service.VacacionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:5173")
public class VacacionesController {
    private final VacacionesService vacacionesService;
    private final ClientValidaToken validaToken;
    private final String endPoint=" ";

    @PostMapping("/vacaciones/crea")
    public ResponseEntity<BaseResponse<VacacionesEntity>> crea(@RequestHeader("Authorization") String tokenExt, @RequestBody VacacionesRequest request) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.crear(request);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @PostMapping("/vacaciones/elimina")
    public ResponseEntity<BaseResponse<VacacionesEntity>> elimina(@RequestHeader("Authorization") String tokenExt, @RequestParam Long codigo, @RequestParam String motivo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.eliminar(codigo,motivo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscaxid/{codigo}")
    public ResponseEntity<BaseResponse<VacacionesEntity>> buscaxid(@RequestHeader("Authorization") String tokenExt, @PathVariable Long codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarXId(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscatodos")
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscatodos(@RequestHeader("Authorization") String tokenExt) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarTodos();
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscaxusucrea/{codigo}")
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscaxusucrea(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarUsuarioCrea(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscaxtrab/{codigo}")
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscaxtrab(@RequestHeader("Authorization") String tokenExt, @PathVariable String codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarTrabajador(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscaxpendaprob01")
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteAprob01(@RequestHeader("Authorization") String tokenExt) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarPendienteAprob01();
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscaxpendaprob02")
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteAprob02(@RequestHeader("Authorization") String tokenExt) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarPendienteAprob02();
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @GetMapping("/vacaciones/buscaxpendcontrol")
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteControl(@RequestHeader("Authorization") String tokenExt) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.buscarPendienteControl();
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @PostMapping("/vacaciones/aprobacion01/{codigo}")
    public ResponseEntity<BaseResponse<VacacionesEntity>> aprobacion01(@RequestHeader("Authorization") String tokenExt, @PathVariable Long codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.aprobacion01(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @PostMapping("/vacaciones/aprobacion02/{codigo}")
    public ResponseEntity<BaseResponse<VacacionesEntity>> aprobacion02(@RequestHeader("Authorization") String tokenExt, @PathVariable Long codigo) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.aprobacion02(codigo);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    @PostMapping("/vacaciones/control")
    public ResponseEntity<BaseResponse<VacacionesEntity>> control(@RequestHeader("Authorization") String tokenExt, @RequestParam Long codigo, @RequestParam String obs) {
        ValidaTokenDto validaTokenDto = getExecValidaToken(tokenExt, endPoint);
        if (!ObjectUtils.isEmpty(validaTokenDto) && validaTokenDto.isRespuesta()) {
            return vacacionesService.controlRevision(codigo,obs);
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NO_ACCESO, Constant.MSJ_NO_ACCESO, Optional.empty()));
        }
    }

    private ValidaTokenDto getExecValidaToken(String token, String endPoint){
        return validaToken.getInfoValidaToken(token, endPoint);
    }

}

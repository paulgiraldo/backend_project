package com.codigo.ms_vacaciones.service;

import com.codigo.ms_vacaciones.entities.VacacionesEntity;
import com.codigo.ms_vacaciones.request.VacacionesRequest;
import com.codigo.ms_vacaciones.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface VacacionesService {
    ResponseEntity<BaseResponse<VacacionesEntity>> crear(VacacionesRequest request);
    ResponseEntity<BaseResponse<VacacionesEntity>> eliminar(Long codigo, String motivo);
    ResponseEntity<BaseResponse<VacacionesEntity>> buscarXId(Long codigo);
    ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarTodos();
    ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarUsuarioCrea(String codigo);
    ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarTrabajador(String codigo);
    ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteAprob01();
    ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteAprob02();
    ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteControl();
    ResponseEntity<BaseResponse<VacacionesEntity>> aprobacion01(Long codigo);
    ResponseEntity<BaseResponse<VacacionesEntity>> aprobacion02(Long codigo);
    ResponseEntity<BaseResponse<VacacionesEntity>> controlRevision(Long codigo, String obs);
}

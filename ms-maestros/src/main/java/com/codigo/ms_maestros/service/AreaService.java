package com.codigo.ms_maestros.service;

import com.codigo.ms_maestros.entities.AreaEntity;
import com.codigo.ms_maestros.request.AreaRequest;
import com.codigo.ms_maestros.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface AreaService {
    ResponseEntity<BaseResponse<AreaEntity>> crear(AreaRequest request);
    ResponseEntity<BaseResponse<AreaEntity>> actualizar(String codigo, AreaRequest request);
    ResponseEntity<BaseResponse<AreaEntity>> eliminar(String codigo, String motivo);
    ResponseEntity<BaseResponse<AreaEntity>> buscarXId(String codigo);
    ResponseEntity<BaseResponse<List<AreaEntity>>> buscarTodos();


}

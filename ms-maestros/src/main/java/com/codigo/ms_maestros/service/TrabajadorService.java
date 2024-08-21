package com.codigo.ms_maestros.service;

import com.codigo.ms_maestros.entities.TrabajadorEntity;
import com.codigo.ms_maestros.request.TrabajadorRequest;
import com.codigo.ms_maestros.response.BaseResponse;
import com.codigo.ms_maestros.response.TrabDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrabajadorService {
    ResponseEntity<BaseResponse<TrabajadorEntity>> crear(TrabajadorRequest request);
    ResponseEntity<BaseResponse<TrabajadorEntity>> actualizar(String codigo, TrabajadorRequest request);
    ResponseEntity<BaseResponse<TrabajadorEntity>> eliminar(String codigo, String motivo);
    ResponseEntity<BaseResponse<TrabajadorEntity>> buscarXId(String codigo);
    ResponseEntity<BaseResponse<List<TrabajadorEntity>>> buscarXArea(List<String> lista);
    ResponseEntity<BaseResponse<List<TrabajadorEntity>>> buscarTodos();
    TrabDto buscarXIdDTO(String codigo);
}

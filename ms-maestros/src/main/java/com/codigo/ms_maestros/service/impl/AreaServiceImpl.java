package com.codigo.ms_maestros.service.impl;

import com.codigo.ms_maestros.constants.Constant;
import com.codigo.ms_maestros.entities.AreaEntity;
import com.codigo.ms_maestros.repository.AreaRepository;
import com.codigo.ms_maestros.request.AreaRequest;
import com.codigo.ms_maestros.response.BaseResponse;
import com.codigo.ms_maestros.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;

    @Override
    public ResponseEntity<BaseResponse<AreaEntity>> crear(AreaRequest request) {
        // Validando que se ingrese un valor de CODIGO en el request
        if (ObjectUtils.isEmpty(request) || request.getAreaCod().isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty()));
        }
        // Validando que el CODIGO No exista
        boolean exist = areaRepository.existsById(request.getAreaCod());
        if (exist) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_EXIST, Constant.MSJ_EXIST, Optional.empty()));
        }
        AreaEntity entity;
        entity = getEntity(request, new AreaEntity());
        entity.setEliminado(Constant.VALOR_NO_ELIMINADO);
        entity.setEliminadoMotivo(Constant.MSJ_NO_ELIMINADO);
        entity.setCreaUsuarioCod(Constant.USUARIO_ADMIN);
        entity.setCreaFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(areaRepository.save(entity))));
    }

    @Override
    public ResponseEntity<BaseResponse<AreaEntity>> actualizar(String codigo, AreaRequest request) {
        // Validando que se ingrese un valor de CODIGO en el request
        if ( ObjectUtils.isEmpty(codigo) || ObjectUtils.isEmpty(request) || request.getAreaCod().isEmpty()  ){
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty()));
        }
        // Validando que se ingrese un valor de CODIGO en el request
        Optional<AreaEntity> datoExtraido = areaRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        // Si ha ingresado un nuevo CODIGO, validar que no exista en la BD
        if (!datoExtraido.get().getAreaCod().equals(request.getAreaCod())){
            if (areaRepository.existsById(request.getAreaCod())){
                return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_EXIST, Constant.MSJ_EXIST, Optional.empty()));
            }
        }
        AreaEntity entity;
        entity = getEntity(request, datoExtraido.get());
        entity.setModificaUsuarioCod(Constant.USUARIO_ADMIN);
        entity.setModificaFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(areaRepository.save(entity))));
    }

    @Override
    public ResponseEntity<BaseResponse<AreaEntity>> eliminar(String codigo, String motivo) {
        // Validando que se ingrese un valor de CODIGO que exista en la BD
        Optional<AreaEntity> datoExtraido = areaRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        datoExtraido.get().setEliminado(Constant.VALOR_SI_ELIMINADO);
        datoExtraido.get().setEliminadoMotivo(motivo);
        datoExtraido.get().setEliminadoUsuarioCod(Constant.USUARIO_ADMIN);
        datoExtraido.get().setEliminadoFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(areaRepository.save(datoExtraido.get()))));
    }

    @Override
    public ResponseEntity<BaseResponse<AreaEntity>> buscarXId(String codigo) {
        Optional<AreaEntity> datos = areaRepository.findById(codigo);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, datos));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<AreaEntity>>> buscarTodos() {
        List<AreaEntity> datos = areaRepository.findAll();
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    private AreaEntity getEntity(AreaRequest request, AreaEntity entity) {
        entity.setAreaCod(request.getAreaCod());
        entity.setAreaNom(request.getAreaNom());
        return entity;
    }

    private Date getTimes() {
        long currenTIme = System.currentTimeMillis();
        return new Date(currenTIme);
    }
}

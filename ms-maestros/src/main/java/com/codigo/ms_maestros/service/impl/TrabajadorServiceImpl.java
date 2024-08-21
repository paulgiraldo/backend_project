package com.codigo.ms_maestros.service.impl;

import com.codigo.ms_maestros.client.ClientValidaToken;
import com.codigo.ms_maestros.constants.Constant;
import com.codigo.ms_maestros.entities.TrabajadorEntity;
import com.codigo.ms_maestros.repository.TrabajadorRepository;
import com.codigo.ms_maestros.request.TrabajadorRequest;
import com.codigo.ms_maestros.response.BaseResponse;
import com.codigo.ms_maestros.response.TrabDto;
import com.codigo.ms_maestros.response.ValidaTokenDto;
import com.codigo.ms_maestros.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements TrabajadorService {
    private final TrabajadorRepository trabajadorRepository;

    @Override
    public ResponseEntity<BaseResponse<TrabajadorEntity>> crear(TrabajadorRequest request) {
        // Validando que se ingrese un valor de CODIGO en el request
        if (ObjectUtils.isEmpty(request) || request.getTrabCod().isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty()));
        }
        // Validando que el CODIGO No exista
        boolean exist = trabajadorRepository.existsById(request.getTrabCod());
        if (exist) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_EXIST, Constant.MSJ_EXIST, Optional.empty()));
        }
        TrabajadorEntity entity;
        entity = getEntity(request, new TrabajadorEntity());
        entity.setEliminado(Constant.VALOR_NO_ELIMINADO);
        entity.setEliminadoMotivo(Constant.MSJ_NO_ELIMINADO);
        entity.setCreaUsuarioCod(Constant.USUARIO_ADMIN);
        entity.setCreaFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(trabajadorRepository.save(entity))));
    }

    @Override
    public ResponseEntity<BaseResponse<TrabajadorEntity>> actualizar(String codigo, TrabajadorRequest request) {
        // Validando que se ingrese un valor de CODIGO en el request
        if ( ObjectUtils.isEmpty(codigo) || ObjectUtils.isEmpty(request) || request.getTrabCod().isEmpty()  ){
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty()));
        }
        // Validando que se ingrese un valor de CODIGO en el request
        Optional<TrabajadorEntity> datoExtraido = trabajadorRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        // Si ha ingresado un nuevo CODIGO, validar que no exista en la BD
        if (!datoExtraido.get().getTrabCod().equals(request.getTrabCod())){
            if (trabajadorRepository.existsById(request.getTrabCod())){
                return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_EXIST, Constant.MSJ_EXIST, Optional.empty()));
            }
        }
        TrabajadorEntity entity;
        entity = getEntity(request, datoExtraido.get());
        entity.setModificaUsuarioCod(Constant.USUARIO_ADMIN);
        entity.setModificaFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(trabajadorRepository.save(entity))));
    }

    @Override
    public ResponseEntity<BaseResponse<TrabajadorEntity>> eliminar(String codigo, String motivo) {
        // Validando que se ingrese un valor de CODIGO que exista en la BD
        Optional<TrabajadorEntity> datoExtraido = trabajadorRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        datoExtraido.get().setEliminado(Constant.VALOR_SI_ELIMINADO);
        datoExtraido.get().setEliminadoMotivo(motivo);
        datoExtraido.get().setEliminadoUsuarioCod(Constant.USUARIO_ADMIN);
        datoExtraido.get().setEliminadoFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(trabajadorRepository.save(datoExtraido.get()))));
    }

    @Override
    public ResponseEntity<BaseResponse<TrabajadorEntity>> buscarXId(String codigo) {
        Optional<TrabajadorEntity> datos = trabajadorRepository.findById(codigo);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, datos));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<TrabajadorEntity>>> buscarXArea(List<String> lista) {
        List<TrabajadorEntity> datos = trabajadorRepository.findByAreaCodIn(lista);
        System.out.println(datos);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<TrabajadorEntity>>> buscarTodos() {
        List<TrabajadorEntity> datos = trabajadorRepository.findAll();
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public TrabDto buscarXIdDTO(String codigo) {
        Optional<TrabajadorEntity> datos = trabajadorRepository.findById(codigo);
        TrabDto trabDto = new TrabDto();
        trabDto.setDni(codigo);
        if (datos.isEmpty()) {
            trabDto.setNom(Constant.VALOR_NO_ELIMINADO);
        } else {
            trabDto.setNom(datos.get().getTrabNom());
        }
        return trabDto;
    }

    private TrabajadorEntity getEntity(TrabajadorRequest request, TrabajadorEntity entity) {
        entity.setTrabCod(request.getTrabCod());
        entity.setDocidentTipo(request.getDocidentTipo());
        entity.setDocidentNro(request.getDocidentNro());
        entity.setTrabNom(request.getTrabNom());
        entity.setTrabEstado(request.getTrabEstado());
        entity.setTrabCargo(request.getTrabCargo());
        entity.setTrabCondicion(request.getTrabCondicion());
        entity.setFechaNacimiento(request.getFechaNacimiento());
        entity.setFechaIngreso(request.getFechaIngreso());
        entity.setFechaTermino(request.getFechaTermino());
        entity.setFechaCese(request.getFechaCese());
        entity.setAreaCod(request.getAreaCod());
        entity.setFotografia(request.getFotografia());
        entity.setPuertaAccesoId(request.getPuertaAccesoId());
        return entity;
    }

    private Date getTimes() {
        long currenTIme = System.currentTimeMillis();
        return new Date(currenTIme);
    }

}

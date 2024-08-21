package com.codigo.ms_vacaciones.service.impl;

import com.codigo.ms_vacaciones.constant.Constant;
import com.codigo.ms_vacaciones.entities.TrabajadorEntity;
import com.codigo.ms_vacaciones.entities.VacacionesEntity;
import com.codigo.ms_vacaciones.repository.TrabajadorRepository;
import com.codigo.ms_vacaciones.repository.VacacionesRepository;
import com.codigo.ms_vacaciones.request.VacacionesRequest;
import com.codigo.ms_vacaciones.response.BaseResponse;
import com.codigo.ms_vacaciones.service.VacacionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacacionesServiceImpl implements VacacionesService {
    private final VacacionesRepository vacacionesRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Override
    public ResponseEntity<BaseResponse<VacacionesEntity>> crear(VacacionesRequest request) {
        // Validando que se ingrese un valor de CODIGO en el request
        if (ObjectUtils.isEmpty(request) || request.getTrabCod().isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOVALIDO, Constant.MSJ_VALOR_NOVALIDO, Optional.empty()));
        }
        // Validando que el CODIGO TRABAJADOR  exista
        Optional<TrabajadorEntity> datoExtraido = trabajadorRepository.findById(request.getTrabCod());
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }

        VacacionesEntity entity;
        entity = getEntity(request, new VacacionesEntity());
        entity.setTrabNom(datoExtraido.get().getTrabNom());
        entity.setAreaCod(datoExtraido.get().getAreaCod());
        entity.setAprobacion01(Constant.VALOR_NO);
        entity.setAprobacion02(Constant.VALOR_NO);
        entity.setControlRevision(Constant.VALOR_NO);
        entity.setEliminado(Constant.VALOR_NO);
        entity.setEliminadoMotivo(Constant.MSJ_NO_ELIMINADO);
        entity.setCreaUsuarioCod(Constant.USUARIO_ADMIN);
        entity.setCreaFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(vacacionesRepository.save(entity))));
    }


    @Override
    public ResponseEntity<BaseResponse<VacacionesEntity>> eliminar(Long codigo, String motivo) {
        // Validando que se ingrese un valor de CODIGO que exista en la BD
        Optional<VacacionesEntity> datoExtraido = vacacionesRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        if (datoExtraido.get().getEliminado().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        if (datoExtraido.get().getAprobacion01().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        datoExtraido.get().setEliminado(Constant.VALOR_SI);
        datoExtraido.get().setEliminadoMotivo(motivo);
        datoExtraido.get().setEliminadoUsuarioCod(Constant.USUARIO_ADMIN);
        datoExtraido.get().setEliminadoFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(vacacionesRepository.save(datoExtraido.get()))));
    }

    @Override
    public ResponseEntity<BaseResponse<VacacionesEntity>> buscarXId(Long codigo) {
        Optional<VacacionesEntity> datos = vacacionesRepository.findById(codigo);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, datos));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarTodos() {
//        List<VacacionesEntity> datos = vacacionesRepository.findAll();

        List<VacacionesEntity> datos = vacacionesRepository.findByEliminado("N");

        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarUsuarioCrea(String codigo) {
        List<VacacionesEntity> datos = vacacionesRepository.findByCreaUsuarioCod(codigo);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarTrabajador(String codigo) {
        List<VacacionesEntity> datos = vacacionesRepository.findByTrabCod(codigo);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteAprob01() {
        List<VacacionesEntity> datos = vacacionesRepository.findByEliminadoAndAprobacion01(Constant.VALOR_NO, Constant.VALOR_NO);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteAprob02() {
        List<VacacionesEntity> datos = vacacionesRepository.findByEliminadoAndAprobacion01AndAprobacion02(Constant.VALOR_NO, Constant.VALOR_SI,Constant.VALOR_NO);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<VacacionesEntity>>> buscarPendienteControl() {
        List<VacacionesEntity> datos = vacacionesRepository.findByEliminadoAndAprobacion02AndControlRevision(Constant.VALOR_NO, Constant.VALOR_SI,Constant.VALOR_NO);
        if (datos.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        } else {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(datos)));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<VacacionesEntity>> aprobacion01(Long codigo) {
        // Validando que se ingrese un valor de CODIGO que exista en la BD
        Optional<VacacionesEntity> datoExtraido = vacacionesRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        if (datoExtraido.get().getEliminado().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        if (datoExtraido.get().getAprobacion01().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        datoExtraido.get().setAprobacion01(Constant.VALOR_SI);
        datoExtraido.get().setAprobacion01UsuarioCod(Constant.USUARIO_ADMIN);
        datoExtraido.get().setAprobacion01Fecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(vacacionesRepository.save(datoExtraido.get()))));
    }

    @Override
    public ResponseEntity<BaseResponse<VacacionesEntity>> aprobacion02(Long codigo) {
        // Validando que se ingrese un valor de CODIGO que exista en la BD
        Optional<VacacionesEntity> datoExtraido = vacacionesRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        if (datoExtraido.get().getEliminado().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        if (datoExtraido.get().getAprobacion01().equals(Constant.VALOR_NO)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        if (datoExtraido.get().getAprobacion02().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        datoExtraido.get().setAprobacion02(Constant.VALOR_SI);
        datoExtraido.get().setAprobacion02UsuarioCod(Constant.USUARIO_ADMIN);
        datoExtraido.get().setAprobacion02Fecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(vacacionesRepository.save(datoExtraido.get()))));
    }

    @Override
    public ResponseEntity<BaseResponse<VacacionesEntity>> controlRevision(Long codigo, String obs) {
        // Validando que se ingrese un valor de CODIGO que exista en la BD
        Optional<VacacionesEntity> datoExtraido = vacacionesRepository.findById(codigo);
        if (datoExtraido.isEmpty()) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_NOEXIST, Constant.MSJ_NOEXIST, Optional.empty()));
        }
        if (datoExtraido.get().getEliminado().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        if (datoExtraido.get().getAprobacion02().equals(Constant.VALOR_NO)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        if (datoExtraido.get().getControlRevision().equals(Constant.VALOR_SI)) {
            return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_VALOR_NOPERMITIDO, Constant.MSJ_VALOR_NOPERMITIDO, Optional.empty()));
        }
        datoExtraido.get().setControlRevision(Constant.VALOR_SI);
        datoExtraido.get().setControlRevisionObs(obs);
        datoExtraido.get().setControlUsuarioCod(Constant.USUARIO_ADMIN);
        datoExtraido.get().setControlFecha(getTimes());

        return ResponseEntity.ok(new BaseResponse<>(Constant.CODE_OK, Constant.MSJ_OK, Optional.of(vacacionesRepository.save(datoExtraido.get()))));
    }

    private VacacionesEntity getEntity(VacacionesRequest request, VacacionesEntity entity) {
        entity.setTrabCod(request.getTrabCod());
        entity.setFechaInicial(request.getFechaInicial());
        entity.setNroDias(request.getNroDias());
        entity.setFechaFinal(request.getFechaFinal());
        entity.setMotivo(request.getMotivo());
        return entity;
    }

    private Date getTimes() {
        long currenTIme = System.currentTimeMillis();
        return new Date(currenTIme);
    }

}

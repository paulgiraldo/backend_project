package com.codigo.ms_vacaciones.repository;

import com.codigo.ms_vacaciones.entities.VacacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacacionesRepository extends JpaRepository<VacacionesEntity, Long> {
    public List<VacacionesEntity> findByCreaUsuarioCod(String codigo);
    public List<VacacionesEntity> findByTrabCod(String codigo);
    public List<VacacionesEntity> findByEliminadoAndAprobacion01(String valorE,String valor01);
    public List<VacacionesEntity> findByEliminadoAndAprobacion01AndAprobacion02(String valorE,String valor01, String valor02);
    public List<VacacionesEntity> findByEliminadoAndAprobacion02AndControlRevision(String valorE,String valor02, String valorC);
    public List<VacacionesEntity> findByEliminado(String codigo);

}

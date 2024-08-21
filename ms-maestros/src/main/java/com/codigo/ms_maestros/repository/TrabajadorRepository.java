package com.codigo.ms_maestros.repository;

import com.codigo.ms_maestros.entities.TrabajadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabajadorRepository extends JpaRepository<TrabajadorEntity,String> {
    public List<TrabajadorEntity> findByAreaCodIn(List<String> lista);

}

package com.codigo.ms_vacaciones.repository;

import com.codigo.ms_vacaciones.entities.TrabajadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajadorRepository extends JpaRepository<TrabajadorEntity,String> {
}

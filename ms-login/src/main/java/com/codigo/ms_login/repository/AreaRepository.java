package com.codigo.ms_login.repository;

import com.codigo.ms_login.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AreaRepository extends JpaRepository<AreaEntity, String> {
    boolean existsByAreaCod(String area);
}

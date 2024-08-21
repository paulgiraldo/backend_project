package com.codigo.ms_login.repository;

import com.codigo.ms_login.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RolRepository extends JpaRepository<RolEntity, String> {
    boolean existsByRolCod(String rol);
}

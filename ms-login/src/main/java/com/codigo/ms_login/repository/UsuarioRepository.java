package com.codigo.ms_login.repository;

import com.codigo.ms_login.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {
    Optional<UsuarioEntity> findByUsuarioCod(String usuarioCod);
    Boolean existsByUsuarioCod(String usuarioCod);
}

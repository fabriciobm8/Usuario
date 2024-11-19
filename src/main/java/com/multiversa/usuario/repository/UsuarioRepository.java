package com.multiversa.usuario.repository;

import com.multiversa.usuario.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

  boolean existsByEmail(String email);
}

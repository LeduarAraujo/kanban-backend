package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    public List<UsuarioEntity> findByEmail(String email);
}

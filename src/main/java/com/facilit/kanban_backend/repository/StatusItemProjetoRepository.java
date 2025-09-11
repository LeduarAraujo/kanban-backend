package com.facilit.kanban_backend.repository;

import com.facilit.kanban_backend.domain.entity.StatusItemProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusItemProjetoRepository extends JpaRepository<StatusItemProjetoEntity, Long> {
    Optional<StatusItemProjetoEntity> findByNome(String nome);
}


